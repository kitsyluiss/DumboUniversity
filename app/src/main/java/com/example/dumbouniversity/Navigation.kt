// Navigation.kt
package com.example.dumbouniversity
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dumbouniversity.DocumentUploadScreen
import com.example.dumbouniversity.PaymentScreen
import com.example.dumbouniversity.StatusScreen

object Routes {
    const val SPLASH         = "splash"
    const val WELCOME        = "welcome"
    const val SIGN_UP        = "signup"
    const val INFO           = "info_explain"
    const val COURSE_DETAILS = "course_details"
    const val ENROLL_INPUT   = "enroll_input"
    const val CLASS_SCHEDULE = "class_schedule"
    const val UPLOAD         = "upload"
    const val PAYMENT        = "payment"
    const val STATUS         = "status"
    const val LOGIN          = "login"
}

@Composable
fun AppNav() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = Routes.SPLASH) {
        composable(Routes.SPLASH)         { SplashScreen(navController) }
        composable(Routes.WELCOME)        { WelcomeScreen(navController) }
        composable(Routes.SIGN_UP)        { CreateAccountScreen(navController) }
        composable(Routes.INFO)           { InfoExplanationScreen(navController) }
        composable(Routes.COURSE_DETAILS) { CourseDetailsScreen(navController) }
        composable(Routes.ENROLL_INPUT)   { EnrollmentInputScreen(navController) }
        composable(Routes.CLASS_SCHEDULE) { ClassScheduleScreen(navController) }
        composable(Routes.UPLOAD)         { DocumentUploadScreen(navController) }
        composable(Routes.PAYMENT)        { PaymentScreen(navController) }
        composable(Routes.STATUS)         { StatusScreen(navController) }
        composable(Routes.LOGIN)          { LoginScreen(navController) }
    }
}
