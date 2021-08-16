package com.example.tagskotlinproject.pojo

data class Results(
        val url: String? = null,
        val title: String? = null,
        val category: String? = null,
        val author: Author? = null,
        val stats: Stats? = null,
        val preview: Preview? = null,
        val published_time: String? = null,
        val content: Content? = null


)