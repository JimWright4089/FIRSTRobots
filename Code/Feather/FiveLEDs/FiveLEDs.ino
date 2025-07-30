
const int LED1 = 5;
const int LED2 = 6;
const int LED3 = 9;
const int LED4 = 10;
const int LED5 = 11;


// the setup function runs once when you press reset or power the board
void setup() {
  // initialize digital pin LED_BUILTIN as an output.
  pinMode(LED1, OUTPUT);
  pinMode(LED2, OUTPUT);
  pinMode(LED3, OUTPUT);
  pinMode(LED4, OUTPUT);
  pinMode(LED5, OUTPUT);
}

// the loop function runs over and over again forever
void loop() {
  digitalWrite(LED1, HIGH);
  delay(2000);              
  digitalWrite(LED2, HIGH);
  digitalWrite(LED3, HIGH);
  delay(2000);              
  digitalWrite(LED1, LOW);
  digitalWrite(LED3, LOW);
  digitalWrite(LED4, HIGH);
  delay(2000);              
  digitalWrite(LED5, HIGH); 
  delay(2000);              
  digitalWrite(LED2, LOW); 
  delay(2000);              
  digitalWrite(LED5, LOW); 
  delay(2000);              
  digitalWrite(LED4, LOW); 
  delay(2000);              
}
