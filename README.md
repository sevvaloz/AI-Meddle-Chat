
# AI Meddle Chat

- This project was developed as part of the undergraduate thesis of Izmir Bakırçay University, Department of Computer Engineering.

## Purpose
- Considering the lack of emotional richness in online communication, it is aimed to design an online messaging application to better understand users' emotional states and use this information to make their communication more meaningful.
- What differentiates this messaging application from others is that it analyzes the text-based message content based on the emotions of 'angry', 'scared', 'happy', 'surprised' and 'sad' and provides the user with suggestions of responses that can be given to the other user according to the result of the analysis directly to the user during the messaging.

## System Development

### 1. Messaging App

- The first step is to design a mobile application where users can communicate with each other by messaging. For this purpose, a mobile application running on the Android platform was developed. The login-logout and registration processes of the users are completed with Firebase Authentication provided by Firebase Cloud.
- The most important function, sending and receiving messages, requires a database. Firestore Database stores data such as user information, chat room information and messages for the messaging system to work.

### 2. Sentiment Analysis Model

- Emotion analysis is performed on the messages received from the other user in the messaging application so that users can more easily communicate their emotional states to others in the online platform. For this purpose, the general language model named 'Bert Base Case' is fine-tuned and adapted to the Turkish language with the dataset named 'Turkish Tweets' consisting of 5 basic emotion labels (angry, scared, happy, surprised, sad).
- This fine-tuned model was published on the Huggingface platform and integrated into the mobile application using the Huggingface Inference API and sentiment analysis was performed on the messages.

### 3. Response Suggestion System

- The outputs of the sentiment analysis performed on the messages are configured into inputs for generating suggestions in response to the relevant message and sent to ChatGPT using the OpenAI API.
- ChatGPT generates 3 suggestion messages in accordance with the emotional state of the other user and presents them to the user through the messaging application.
- By using this response suggestion system, the probability and speed of users responding to messages is increased.

## Technologies

- Android
- Kotlin
- Firebase Cloud (Firestore Db, Authentication)
- Kaggle
- Python
- Huggingface (Inference API)
- ChatGPT (OpenAI API)

## Screenshots

<img src='https://github.com/sevvaloz/AI-Meddle-Chat/assets/70901471/6db523aa-5980-4895-a19c-9461f3993943' width='195' height='425'>
<img src='https://github.com/sevvaloz/AI-Meddle-Chat/assets/70901471/02b6dbdf-8580-4af1-92cd-7ea0f2feea6e' width='195' height='425'>
<img src='https://github.com/sevvaloz/AI-Meddle-Chat/assets/70901471/a18fc786-8f24-488a-95a0-f2f6f8324192' width='195' height='425'>
<img src='https://github.com/sevvaloz/AI-Meddle-Chat/assets/70901471/c46ec770-ecec-487a-866e-57d9f011801b' width='195' height='425'>

## Turkish Presentation File
https://docs.google.com/presentation/d/1RhlthbN7yt0e2BNWxw6n4rRN7JMbtQFQ/edit?usp=sharing&ouid=114502975451362089998&rtpof=true&sd=true
