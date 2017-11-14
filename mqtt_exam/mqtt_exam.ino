#include <WiFi.h>
#include <WiFiClient.h>
#include "DHT11.h"
#include "PubSubClient.h"
#include <Wire.h> 
#include "LiquidCrystal_I2C.h"
LiquidCrystal_I2C lcd(0x3F,16,2);
char ssid[] = "CONFERENCE_ROOM_3";//공유기 이름
char password[] = "welcomegsc";//공유기 암호
int status = WL_IDLE_STATUS;
byte server[] = { 192,168,0,35 };//MQTT브로커 IP
int port = 1883;//MQTT브로커 포트
DHT11 dht11(2);//온도습도센터객체 (디지털2번핀)
WiFiClient client;//와이파이클라이언트 객체
void msgReceived(char *topic, byte *payload, unsigned int uLen) {//콜백함수
  char pBuffer[uLen+1]; int i;
  for (i = 0; i < uLen; i++) pBuffer[i] = payload[i];//메시지 수신
  pBuffer[i] = '\0';
  Serial.println(pBuffer);//누군가가 발행한 메시지를 출력
  lcd.clear();
  lcd.print(pBuffer);
  if (pBuffer[0] == '1') digitalWrite(3,HIGH);
  else if (pBuffer[0] == '0') digitalWrite(3,LOW);
  if (pBuffer[0] == '2') digitalWrite(5,HIGH);
  else if (pBuffer[0] == '3') digitalWrite(5,LOW);
  if (pBuffer[0] == '4') digitalWrite(6,HIGH);
  else if (pBuffer[0] == '5') digitalWrite(6,LOW);
  if (pBuffer[0] == '6') digitalWrite(8,HIGH);
  else if (pBuffer[0] == '7') digitalWrite(8,LOW);
}
PubSubClient mqttClient(server,port,msgReceived,client);//MQTT클라이언트 객체생성
void setup() {
  lcd.begin();              
  lcd.backlight();
  lcd.print("Hello, world!");
  pinMode(4,INPUT);
  pinMode(3,OUTPUT); pinMode(5,OUTPUT); pinMode(6,OUTPUT); pinMode(8,OUTPUT);
  Serial.begin(9600);
  if (WiFi.status() == WL_NO_SHIELD) while(true);//와이파이쉴드없으면 종료
  while(status != WL_CONNECTED) { 
    status = WiFi.begin(ssid,password); delay(3000);//공유기 접속시도
  }
  Serial.println("Wi-Fi AP Connected!");//공유기 접속 성공
  if (mqttClient.connect("Arduino")) {//MQTT브로커에 접속시도
    Serial.println("MQTT broker Connected!");
    mqttClient.subscribe("test");//arduino 토픽에 대해 구독신청
    mqttClient.publish("test", "Hello, Arduino");//arduino토픽으로 메시지 발행
  }
}
void loop() {
  mqttClient.loop();
  float temp,humidity;
  int err = dht11.read(humidity,temp);
  if (err == 0) {
    char message[64] = "", pTempBuffer[50], pHumidityBuffer[50];
    dtostrf(temp , 5, 2, pTempBuffer); dtostrf(humidity , 5, 2, pHumidityBuffer);
    sprintf(message, "{\"temp\":%s,\"humidity\":%s}", pTempBuffer,pHumidityBuffer);
    mqttClient.publish("arduino", message);
  }
  int digital = digitalRead(4);
  int analog = analogRead(A0);
  {
    char message[64] = "";
    sprintf(message, "{\"digital\":%d,\"analog\":%d}", digital,analog);
    mqttClient.publish("mq2", message);
  }
}
