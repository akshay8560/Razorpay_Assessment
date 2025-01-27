# Razorpay Assessment

This project is a task management application built using Jetpack Compose, Room, and Firebase. It allows users to add, edit, and complete tasks while tracking events and performance using Firebase Analytics and Performance Monitoring.

## Setup and Run Instructions

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/akshay8560/Razorpay_Assessment.git
   cd razorpay-assessment
   ```

2. **Open in Android Studio**:
   - Open Android Studio and select "Open an existing Android Studio project."
   - Navigate to the cloned repository and open it.

3. **Configure Firebase**:
   - Add  `google-services.json` file to the `app/` directory. This file is obtained from the Firebase Console when you set up your project.

4. **Build and Run**:
   - Ensure your Android device or emulator is connected.
   - Click on the "Run" button in Android Studio to build and run the app.

## API Details

- **API URL**: ([https://api.example.com](https://jsonplaceholder.typicode.com/todos))  

- **Endpoints**:
  - `GET /todos`: Fetches a list of tasks.
  - `POST /todos`: Adds a new task.
  - `PUT /todos/{id}`: Updates an existing task.
  - `DELETE /todos/{id}`: Deletes a task.

## Third-Party Library Integration

- **Firebase**:
  - **Analytics**: Used to track key events such as "Task Added," "Task Edited," and "Task Completed."
  - **Crashlytics**: Used to log and monitor crashes.
  - **Performance Monitoring**: Used to monitor network performance and app start times.

- **Retrofit**: Used for making network requests to fetch tasks from an API.

- **Room**: Used for local database storage of tasks.

## Design Decisions

- **Jetpack Compose**: Chosen for its modern declarative UI approach, allowing for a more intuitive and efficient UI development process.
- **Room Database**: Provides a robust and efficient way to handle local data storage with support for LiveData and coroutines.
- **Firebase Integration**: Offers comprehensive analytics, crash reporting, and performance monitoring, which are crucial for maintaining app quality and user experience.

## Screenshots and Recordings

### Firebase Analytics Console

![Firebase Analytics Events]([https://drive.google.com/file/d/1FNbi8MnzKh0-VCUflV7gK4j50yCKgPii/view?usp=sharing]))

### Mobile Screen Recording 

![Screen Recording Video](https://www.youtube.com/shorts/6khK7akZ64g)

### Firebase Crashlytics Console

![Firebase Crashlytics]([path/to/crashlytics_screenshot.png](https://drive.google.com/file/d/1wW9nCtrumUvbL0QiO3vdif1w5UQL73YO/view?usp=sharing))

### Firebase Performance Monitoring

![Network Performance]([path/to/performance_screenshot.png](https://drive.google.com/file/d/1EAm-ZN_rBBlsypYZEAs9d8rwhGA4kQnJ/view?usp=sharing))

## APK LINK

![APK]([https://drive.google.com/file/d/1Q0_Ng2HkOrbCY-jfSQZqqWMJdYIvFPyJ/view?usp=sharing])




