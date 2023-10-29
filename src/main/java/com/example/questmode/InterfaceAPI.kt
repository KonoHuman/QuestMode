package com.example.questmode

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface OpenAIApi {
    @Headers("Authorization: Bearer TA_CLÉ_API")
    @POST("https://api.openai.com/v1/engines/davinci-codex/completions")
    fun completeText(@Body prompt: PromptModel): Call<ResponseModel>
}

data class ResponseModel(
    val id: String,
    val object: String,
    val created: Int,
    val model: String,
    val choices: List<Choice>
)

data class Choice(
    val text: String,
    val index: Int,
    val logprobs: Any,
    val finish_reason: String
)

data class PromptModel(
    val prompt: String,
    val max_tokens: Int
)
