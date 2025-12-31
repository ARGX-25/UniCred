## High-Level Data Flow

```
Backend (FastAPI)
    ↓
DTO (Network Model)
    ↓
Mapper
    ↓
Repository   ←––– THE PIVOT LAYER
    ↓
Domain Model
    ↓
ViewModel
    ↓
UI (Compose)
```

Each arrow represents a **strict boundary**.
Data is transformed intentionally at each step.

---

## 1. Backend (FastAPI)

* Owns the database schema
* Exposes REST APIs
* Can change independently of the Android app

The backend does **not** care how the UI is built.

---

## 2. DTOs (Network Models)

**Location:** `models/network/`

DTOs represent the **exact shape of backend JSON responses and requests**.

Characteristics:

* Annotated with `@SerializedName`
* Mirrors backend field names
* Can change if the API changes
* Never used directly in UI or ViewModels

Example:

```kotlin
data class StudentDTO(
    @SerializedName("student_id") val studentId: String,
    @SerializedName("full_name") val fullName: String,
    val email: String
)
```

DTOs are **not business objects**.
They are transport containers.

---

## 3. Mappers

**Location:** `models/domain/mapper/` or `models/network/mapper/`

Mappers convert DTOs into **domain models**.

Why this layer exists:

* Backend fields ≠ App concepts
* Backend may add/remove/rename fields
* Domain must remain stable

Example:

```kotlin
fun StudentDTO.toDomain(): Student {
    return Student(
        id = studentId,
        name = fullName,
        email = email
    )
}
```

Mappers are:

* Stateless
* Pure functions
* Easy to test

---

## 4. Repository (THE PIVOT)

**Location:** `repositories/`

The repository is the **single source of truth** for data access.

Responsibilities:

* Call APIs
* Receive DTOs
* Convert DTOs → Domain models
* Decide where data comes from (network / cache / DB later)

What repositories expose:

```kotlin
suspend fun getStudents(): List<Student>
```

What repositories must NEVER expose:

* DTOs
* Retrofit responses
* Network exceptions directly

Think of the repository as **customs control** between the outside world and your app.

---

## 5. Domain Models

**Location:** `models/domain/`

Domain models represent **business concepts**, not API shapes.

Characteristics:

* Clean Kotlin data classes
* No JSON annotations
* No Retrofit / Gson imports
* Used everywhere above the repository

Example:

```kotlin
data class Student(
    val id: String,
    val name: String,
    val email: String
)
```

If the backend changes tomorrow, **domain models should not**.

---

## 6. ViewModel

**Location:** `viewmodel/`

ViewModels:

* Call repositories
* Hold UI state (`StateFlow`, `MutableState`)
* Perform simple transformations for display
* Never talk to DTOs or APIs directly

Example responsibilities:

* Loading states
* Error states
* Mapping domain data → UI state

ViewModels are lifecycle-aware and UI-agnostic.

---

## 7. UI (Jetpack Compose)

**Location:** `ui/screens/`

The UI:

* Consumes ViewModel state
* Displays domain data
* Emits user intents (clicks, input)

The UI:

* Does NOT fetch data
* Does NOT know repositories
* Does NOT know DTOs

It only reacts.

---

## Direction Rules (Very Important)

* Data flows **downward**
* Intents flow **upward**
* No layer skips another
* No circular dependencies

If a layer knows *too much*, it’s in the wrong place.

---

## Why This Architecture Matters

This structure ensures:

* Backend can evolve independently
* UI can be redesigned without touching data logic
* Testing is simple and isolated
* Offline caching can be added later
* Multiple backends can coexist


