sealed class Screen(val route: String) {
    object NoteScreen : Screen("note_screen")
    object NoteDetailsScreen : Screen("note_details_screen")
    object AddNoteScreen : Screen("add_note_screen")
    object SplashScreen : Screen("splash_screen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}