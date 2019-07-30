package dev.qbikkx.conferences.core

data class Conference(
    val name: String,
    val url: String,
    val startDate: String,
    val endDate: String,
    val city: String,
    val country: String,
    val categories: List<Category>,
    val cfpUrl: String? = null,
    val cfpEndDate: String? = null,
    val twitter: String? = null
) {

    enum class Category(val value: String) {
        Android("android"),
        Clojure("clojure"),
        CSS("css"),
        Data("data"),
        DevOps("devops"),
        dotNet("dotnet"),
        Elixir("elixir"),
        General("general"),
        GoLang("golang"),
        GraphQL("graphql"),
        iOS("ios"),
        Java("java"),
        JavaScript("javascript"),
        Networking("networking"),
        PHP("php"),
        Python("python"),
        Ruby("ruby"),
        Rust("rust"),
        Scala("scala"),
        Security("security"),
        TechComm("tech-comm"),
        UX("ux");

        companion object {
            fun fromString(value: String): Category = values().first { it.value == value }
        }
    }
}