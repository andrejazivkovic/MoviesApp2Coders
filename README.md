# 🎬 Movie List App

Welcome to the Movie List App! This Android application allows you to explore a vast database of movies sourced from the TMDB (The Movie Database) API.

## 📽️ Features

### Infinite Scrolling & Pagination
- Efficiently loads data using pagination to minimize request frequency and enhance performance.

### Detailed Movie Information
- Each movie has a title, poster, rating, overview, release date, and original language.

### Offline Mode
- The app supports offline functionality using Room, allowing you to view your favorite movies without an internet connection.

### Watch Later Functionality
- Users can add movies to their "Watch Later" list, ensuring they never miss a movie they want to see.

### Search Functionality
- Easily search for movies or TV shows. Choose your preference and get instant results from the TMDB API.
- The search functionality employs throttling to reduce the frequency of API calls, preventing spamming while still delivering quick results.

## 🏗️ Architecture

- **Clean Architecture**: The project follows a clean architecture to keep the code organized and maintainable.
- **MVVM Pattern**: Utilizes the Model-View-ViewModel architecture for better separation of concerns.
- **Kotlin Coroutines**: Makes asynchronous programming smooth and efficient.
- **Sealed Classes & Enums**: Implements sealed classes for better state management.

### 📡 Networking
- **Retrofit & OkHttp**: Used for HTTP client and request handling, ensuring robust and efficient network operations.

### 🧪 Testing
- All functionality is unit-tested to ensure a reliable experience for users.

## ⚙️ **Clone the repository** Getting Started

### Prerequisites
- You will need to generate an API key from TMDB.
- Create a `local.properties` file in your project root (if it doesn't already exist) and add your API key:
    ```
    api.key="your-generated-key"
    ```


## 🎥 Quick Preview of the design
<img src="https://github.com/andrejazivkovic/MoviesApp2Coders/blob/master/app/src/main/res/drawable/main_screen.jpeg"
alt="main screen"
style="border-radius: 30%; width: 400px; height: 948px; object-fit: cover;"/>
<img src="https://github.com/andrejazivkovic/MoviesApp2Coders/blob/master/app/src/main/res/drawable/details_screen.jpeg"
alt="details screen"
style="border-radius: 30%; width: 400px; height: 948px; object-fit: cover;"/>
<img src="https://github.com/andrejazivkovic/MoviesApp2Coders/blob/master/app/src/main/res/drawable/search_tv_show.jpeg"
alt="search screen"
style="border-radius: 30%; width: 400px; height: 948px; object-fit: cover;"/>
<img src="https://github.com/andrejazivkovic/MoviesApp2Coders/blob/master/app/src/main/res/drawable/watch_later.jpeg"
alt="watch later"
style="border-radius: 30%; width: 400px; height: 948px; object-fit: cover;"/>

