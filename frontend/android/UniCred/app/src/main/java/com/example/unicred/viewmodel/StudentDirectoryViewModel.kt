package com.example.unicred.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unicred.models.domain.Student
import com.example.unicred.repositories.StudentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class StudentDirectoryUiState(
    val isLoading: Boolean = false,
    val students: List<Student> = emptyList(),
    val filteredStudents: List<Student> = emptyList(),
    val searchQuery: String = "",
    val errorMessage: String? = null,

    val totalCount: Int = 0,
    val activeCount: Int = 0,
    val graduatedCount: Int = 0,
    val suspendedCount: Int = 0
)

class StudentDirectoryViewModel(
    private val studentRepository: StudentRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(StudentDirectoryUiState())
    val uiState: StateFlow<StudentDirectoryUiState> = _uiState.asStateFlow()

    init {
        loadStudents()
    }

    fun loadStudents() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            try {
                val students = studentRepository.getStudents()

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        students = students,
                        filteredStudents = applySearch(
                            students,
                            it.searchQuery
                        ),
                        totalCount = students.size,
                        activeCount = students.count { s -> s.status.equals("ACTIVE", true) },
                        graduatedCount = students.count { s -> s.status.equals("GRADUATED", true) },
                        suspendedCount = students.count { s -> s.status.equals("SUSPENDED", true) }
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "Unable to load students"
                    )
                }
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        _uiState.update { state ->
            state.copy(
                searchQuery = query,
                filteredStudents = applySearch(state.students, query)
            )
        }
    }

    private fun applySearch(
        students: List<Student>,
        query: String
    ): List<Student> {
        if (query.isBlank()) return students

        val q = query.trim().lowercase()

        return students.filter { student ->
            student.fullName.lowercase().contains(q) ||
                    student.email.lowercase().contains(q) ||
                    student.studentId.lowercase().contains(q)
        }
    }
}
