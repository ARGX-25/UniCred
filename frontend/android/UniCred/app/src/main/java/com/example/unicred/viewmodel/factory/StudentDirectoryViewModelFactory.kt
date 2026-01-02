package com.example.unicred.viewmodel.factory

//The only reason we are doing this is because we have not implemented dependency injections

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.unicred.repositories.StudentRepository
import com.example.unicred.viewmodel.StudentDirectoryViewModel

class StudentDirectoryViewModelFactory(
    private val repository: StudentRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StudentDirectoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return StudentDirectoryViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
