package es.queryinformatica.clean_architecture

private const val ROUTE_USERS = "users"
private const val ROUTE_USER = "users/%s"
private const val ARGUMENT_USER_NAME = "name"

sealed class AppNavigation (val route: String, val argumentName: String = "") {
    object Users : AppNavigation(ROUTE_USERS)
    object User : AppNavigation(String.format(ROUTE_USER, "{${ARGUMENT_USER_NAME}}"), ARGUMENT_USER_NAME){
        fun routeForName(name: String) = String.format(ROUTE_USER, name)
    }
}