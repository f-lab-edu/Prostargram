# 🌠 Prostargram
개발에 관련된 일상을 공유하는 `SNS(Social Network Services)`입니다.

※ 프로젝트에 대해 더 자세히 알고 싶으시다면 [Wiki](https://github.com/f-lab-edu/Prostargram/wiki)를 참고해주시길 바랍니다.

# Introduction
## 💡 Planning 
개발을 뜻하는 `Pro`gramming과 대표적인 SNS 플랫폼 In`stagram`에서 아이디어를 얻고 기획하였습니다.

## 📚 Tech Stack
### Backend
`Java 17`, `Spring boot`, `MySQL`, `RabbitMQ`, `Redis`, `Docker`, `Naver Cloud Platform`, `Grafana`, `Prometheus`
### [Frontend](https://github.com/f-lab-edu/Prostargram-frontend)
`Next.js`. `React`, `TypeScript`, `Tanstack-query`

## ✏️ Period
구현 : `2023.07 ~ 2024.01`

리팩토링 : `2024.08` ~

## 🔖 Tech Topic
프로젝트에 대한 간략한 소개는 다음 링크를 참고해주시길 바랍니다. ([링크](https://docs.google.com/presentation/d/1SL7JEb8aAzBXU16tI7uoVpilk27ZLOj70KdnUQ9USdE/edit?usp=sharing))
### 1️⃣ Push Model (FanOut-On-Write)
#### 피드 발행
- RabbitMQ를 활용한 FanOut Server(Consumer) 코드는 이곳을 참조해주시길 바랍니다. ([링크](https://github.com/eunbileeme/Prostargram-Consumer))
<img width="917" alt="image" src="https://github.com/user-attachments/assets/f14020d6-4f9c-4a66-9b40-0d067e8593de">

#### 피드 조회
<img width="924" alt="image" src="https://github.com/user-attachments/assets/78855571-612c-436b-94c9-f909b91d2155">

### 2️⃣ Pull Model (FanOut-On-Read)
#### 피드 발행
<img width="924" alt="image" src="https://github.com/user-attachments/assets/0f77c654-d6b2-46e2-aa6b-41416b1b79a3">

#### 피드 조회
<img width="924" alt="image" src="https://github.com/user-attachments/assets/59cf655a-1197-4344-a666-ab3426ec28e0">

## 📝 Server Architecture
<img width="924" alt="image" src="https://github.com/user-attachments/assets/d233b76f-27fa-456a-bbe7-5b6f17866e3b">

## 🧾 [ERD(Entity Relationship Diagram)](https://www.erdcloud.com/d/RCprTk7yCrjyE7kWq)
<img width="924" alt="image" src="https://github.com/user-attachments/assets/acaec79b-0beb-4a83-93f3-769a7f5975b9">

## 🖼️ [Prototype](https://www.figma.com/design/5sskEbduPRM483B86tabRy/Prostagram?node-id=330-190&t=afdzsi5ib1yal9Fo-1)
<img width="924" alt="image" src="https://github.com/user-attachments/assets/bd901a1d-6d9e-4324-9b4b-83c62b8df842">

