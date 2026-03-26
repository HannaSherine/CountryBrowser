# Country Browser 🌍

An Android app that fetches and displays information about countries around the world using the [RestCountries API](https://restcountries.com).

Built as a learning project to understand REST API calls, JSON parsing, and Android architecture patterns.

---

## Features

- Browse all 250+ countries in a scrollable list
- Search countries by name in real time
- Tap any country to see detailed info — capital, region, population, and languages
- Country flags loaded from the web

---

## Screenshots

| List screen | Detail screen |
|---|---|
| ![List](screenshots/list.png) | ![Detail](screenshots/detail.png) |

---

## Tech Stack

| What | Why |
|---|---|
| [Retrofit](https://square.github.io/retrofit/) | HTTP client for making API calls |
| [Gson](https://github.com/google/gson) | Converts JSON response into Kotlin objects |
| [Glide](https://bumptech.github.io/glide/) | Loads and caches flag images |
| [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) | Runs network calls off the main thread |
| ViewModel + LiveData | Survives screen rotation, drives UI updates |
| RecyclerView + DiffUtil | Efficiently renders and updates the country list |

---

## Architecture

This app follows the **MVVM (Model-View-ViewModel)** pattern:

```
UI (Activity)
    ↕ observes LiveData
ViewModel
    ↕ calls suspend functions
Repository
    ↕ makes HTTP request
Retrofit API Service
    ↕ fetches JSON
RestCountries API
```

- **Activity** — sets up RecyclerView, observes ViewModel, handles navigation
- **ViewModel** — holds and manages data, survives rotation, handles search filtering
- **Repository** — single source of truth for data, wraps API calls in try/catch
- **Model** — Kotlin data classes that mirror the JSON response structure

---

## Project Structure

```
com.yourapp.countrybrowser
│
├── data/
│   ├── model/
│   │   └── Country.kt          # data classes matching the API JSON
│   ├── api/
│   │   └── CountryApiService.kt  # Retrofit interface
│   └── repository/
│       └── CountryRepository.kt  # wraps API calls, handles errors
│
├── ui/
│   ├── list/
│   │   ├── CountryListActivity.kt
│   │   ├── CountryListViewModel.kt
│   │   └── CountryAdapter.kt
│   └── detail/
│       └── CountryDetailActivity.kt
│
└── utils/
    └── Resource.kt              # sealed class for Loading/Success/Error states
```

---

## How It Works

1. App opens → `CountryListActivity` starts → `ViewModel` is created
2. ViewModel launches a coroutine and calls the repository
3. Repository makes a GET request to `https://restcountries.com/v3.1/all`
4. Gson converts the JSON response into `List<Country>` Kotlin objects
5. Result is wrapped in `Resource.Success` and posted to `LiveData`
6. Activity observes the LiveData change and calls `adapter.submitList()`
7. `DiffUtil` compares old and new list, only redraws rows that changed
8. Glide loads each country flag image in the background

---

## Getting Started

### Prerequisites

- Android Studio Hedgehog or newer
- Android SDK 26+
- Internet connection

### Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/country-browser.git
   ```

2. Open in Android Studio

3. Run on an emulator or physical device (API 26+)

No API key needed — RestCountries is free and open.

---

## API

This app uses the [RestCountries v3.1 API](https://restcountries.com).

Example endpoint:
```
GET https://restcountries.com/v3.1/all?fields=name,capital,region,population,area,flags,languages
```

Example response for one country:
```json
{
  "name": { "common": "Canada", "official": "Canada" },
  "capital": ["Ottawa"],
  "region": "Americas",
  "population": 41651653,
  "flags": { "png": "https://flagcdn.com/w320/ca.png" },
  "languages": { "eng": "English", "fra": "French" }
}
```

---

## What I Learned

- How to make network calls using Retrofit and Kotlin Coroutines
- How JSON maps to Kotlin data classes
- MVVM architecture and separation of concerns
- How RecyclerView and Adapters work
- How DiffUtil improves RecyclerView performance
- How Glide loads and caches remote images
- Handling loading, success, and error states with a sealed class

---

## License

MIT License — feel free to use this project for learning.
