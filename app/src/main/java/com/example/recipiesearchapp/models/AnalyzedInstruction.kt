package com.example.recipiesearchapp.models

data class AnalyzedInstruction(
    val name: String,
    val steps: List<Step>
)