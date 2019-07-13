package dev.qbikkx.conferences.core

data class Conference(
    val name: String,
    val url: String,
    val startDate: String,
    val endDate: String,
    val city: String,
    val country: String,
    val category : Category,
    val cfpUrl: String? = null,
    val cfpEndDate: String? = null,
    val twitter: String? = null
) {

    sealed class Category(val value: String) {
        object Android : Category("android")
        object Clojure : Category("clojure")
        object CSS : Category("css")
        object Data : Category("data")
        object DevOps : Category("devops")
        object dotNet : Category("dotnet")
        object Elixir : Category("elixir")
        object General : Category("general")
        object GoLang : Category("golang")
        object GraphQL : Category("graphql")
        object iOS : Category("ios")
        object Java : Category("java")
        object JavaScript : Category("javascript")
        object Networking : Category("networking")
        object PHP : Category("php")
        object Python : Category("python")
        object Ruby : Category("ruby")
        object Rust : Category("rust")
        object Scala : Category("scala")
        object Security : Category("security")
        object TechComm : Category("tech-comm")
        object UX : Category("ux")
    }
}