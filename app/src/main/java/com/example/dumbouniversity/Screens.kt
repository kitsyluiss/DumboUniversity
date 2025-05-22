// Screens.kt
package com.example.dumbouniversity

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(nav: NavHostController) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    var user by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text("Login") },
                modifier = Modifier.statusBarsPadding()
            )
        }
    ) { inner ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(inner)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("SmartEnroll", style = MaterialTheme.typography.h4)
            OutlinedTextField(
                value = user,
                onValueChange = { user = it },
                label = { Text("Username") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = pass,
                onValueChange = { pass = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = {
                    val found = EnrollRepository.users.find { it.username == user && it.password == pass }
                    if (found != null) {
                        nav.navigate(Routes.STATUS) {
                            popUpTo(Routes.LOGIN) { inclusive = true }
                        }
                    } else {
                        scope.launch {
                            scaffoldState.snackbarHostState.showSnackbar("Invalid credentials")
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Login")
            }
            TextButton(
                onClick = { nav.navigate(Routes.SIGN_UP) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("First-time? Register here")
            }
        }
    }
}

@Composable
fun SplashScreen(nav: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("") },
                modifier = Modifier.statusBarsPadding(),
                elevation = 0.dp,
                backgroundColor = Color.Transparent
            )
        }
    ) { inner ->
        Box(
            Modifier
                .fillMaxSize()
                .padding(inner),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(R.drawable.dumbo),
                contentDescription = "Logo",
                modifier = Modifier.size(128.dp)
            )
            CircularProgressIndicator(
                Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 64.dp)
            )
        }
        LaunchedEffect(Unit) {
            if (EnrollRepository.courses.isEmpty()) {
                EnrollRepository.courses.addAll(
                    listOf(
                        Course("Course 1","Intro to X"),
                        Course("Course 2","Basics of Y"),
                        Course("Course 3","Advanced Z")
                    )
                )
            }
            delay(2000)
            nav.navigate(Routes.WELCOME) {
                popUpTo(Routes.SPLASH) { inclusive = true }
            }
        }
    }
}

@Composable
fun WelcomeScreen(nav: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Welcome") },
                modifier = Modifier.statusBarsPadding()
            )
        }
    ) { inner ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(inner)
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Dumbo University", style = MaterialTheme.typography.h4)
            Spacer(Modifier.height(24.dp))
            Button({ nav.navigate(Routes.SIGN_UP) }, Modifier.fillMaxWidth()) {
                Text("SIGN UP")
            }
            Spacer(Modifier.height(16.dp))
            OutlinedButton({ nav.navigate(Routes.LOGIN) }, Modifier.fillMaxWidth()) {
                Text("LOG IN")
            }
        }
    }
}

@Composable
fun CreateAccountScreen(nav: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Create Account") },
                modifier = Modifier.statusBarsPadding()
            )
        }
    ) { inner ->
        var firstName by remember { mutableStateOf("") }
        var lastName  by remember { mutableStateOf("") }
        var email     by remember { mutableStateOf("") }
        var phone     by remember { mutableStateOf("") }
        var year      by remember { mutableStateOf("") }
        var month     by remember { mutableStateOf("") }
        var day       by remember { mutableStateOf("") }
        var pwd       by remember { mutableStateOf("") }
        var confirm   by remember { mutableStateOf("") }
        var error     by remember { mutableStateOf<String?>(null) }

        Column(
            Modifier
                .fillMaxSize()
                .padding(inner)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("CREATE ACCOUNT", style = MaterialTheme.typography.h5)
            fun allFilled() = listOf(firstName,lastName,email,phone,year,month,day,pwd,confirm)
                .all { it.isNotBlank() } && pwd==confirm

            OutlinedTextField(
                value = firstName,
                onValueChange = { firstName = it },
                label = { Text("First Name") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = lastName,
                onValueChange = { lastName = it },
                label = { Text("Last Name") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text("Phone Number") },
                modifier = Modifier.fillMaxWidth()
            )
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = year,
                    onValueChange = { year = it },
                    label = { Text("Year") },
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = month,
                    onValueChange = { month = it },
                    label = { Text("Month") },
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = day,
                    onValueChange = { day = it },
                    label = { Text("Day") },
                    modifier = Modifier.weight(1f)
                )
            }
            OutlinedTextField(
                value = pwd,
                onValueChange = { pwd = it },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation()
            )
            OutlinedTextField(
                value = confirm,
                onValueChange = { confirm = it },
                label = { Text("Confirm Password") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation()
            )

            error?.let {
                Text(it, color = MaterialTheme.colors.error, style = MaterialTheme.typography.caption)
            }

            Spacer(Modifier.weight(1f))
            Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Button(onClick = {
                    if (!allFilled()) {
                        error = "Please fill all fields & match passwords."
                    } else {
                        val stud = Student("$firstName $lastName", phone)
                        EnrollRepository.students.add(stud)
                        EnrollRepository.users.add(User(email, pwd, Role.STUDENT))
                        nav.navigate(Routes.INFO)
                    }
                }) {
                    Text("Create Account")
                }
            }
        }
    }
}

@Composable
fun InfoExplanationScreen(nav: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("How to Use App") },
                modifier = Modifier.statusBarsPadding()
            )
        }
    ) { innerPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Spacer(Modifier.height(16.dp))

            // Four‐item 2×2 grid
            val features = listOf(
                "Fast Registration" to "Explanation of Fast Registration",
                "Digital Verification" to "Explanation of Digital Verification",
                "Real-time Tracking" to "Explanation of Real-time Tracking",
                "Secure Payments" to "Explanation of Secure Payments"
            )

            features
                .chunked(2)
                .forEach { row ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        row.forEach { (title, desc) ->
                            Column(
                                modifier = Modifier
                                    .weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Box(
                                    Modifier
                                        .size(200.dp)
                                        .background(Color.Gray, RoundedCornerShape(8.dp))
                                )
                                Spacer(Modifier.height(8.dp))
                                Text(title, style = MaterialTheme.typography.subtitle1)
                                Text(
                                    desc,
                                    style = MaterialTheme.typography.body2,
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                            }
                        }
                    }
                }

            Spacer(Modifier.weight(1f))

            // Next button
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Button(onClick = { nav.navigate(Routes.COURSE_DETAILS) }) {
                    Text("Next")
                }
            }
        }
    }
}

@Composable
fun CourseDetailsScreen(nav: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Courses") },
                modifier = Modifier.statusBarsPadding()
            )
        }
    ) { inner ->
        LazyColumn(
            Modifier.fillMaxSize().padding(inner).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(EnrollRepository.courses) { course ->
                Card(Modifier.fillMaxWidth()) {
                    Row(
                        Modifier.padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(course.code)
                        TextButton(onClick={ /* details */ }) { Text("More") }
                    }
                }
            }
            item {
                Spacer(Modifier.height(16.dp))
                Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Button(onClick={nav.navigate(Routes.ENROLL_INPUT)}) { Text("Next") }
                }
            }
        }
    }
}

@Composable
fun EnrollmentInputScreen(nav: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Enrollment Input") },
                modifier = Modifier.statusBarsPadding()
            )
        }
    ) { inner ->
        Column(
            Modifier.fillMaxSize().padding(inner).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            repeat(6){
                OutlinedTextField(
                    value="",
                    onValueChange={},
                    label={Text("Field ${it+1}")},
                    modifier=Modifier.fillMaxWidth()
                )
            }
            Spacer(Modifier.weight(1f))
            Box(Modifier.fillMaxWidth(),contentAlignment=Alignment.Center){
                Button(onClick={nav.navigate(Routes.CLASS_SCHEDULE)}){Text("Next")}
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ClassScheduleScreen(nav: NavHostController) {
    Scaffold(
        topBar={
            TopAppBar(
                title={Text("Class Schedule")},
                modifier=Modifier.statusBarsPadding()
            )
        }
    ){inner->
        var expanded by remember{mutableStateOf(false)}
        var selected by remember{mutableStateOf("Select Subject")}
        val subjects=listOf("Math","Science","History","Art")

        Column(
            Modifier.fillMaxSize().padding(inner).padding(16.dp),
            verticalArrangement=Arrangement.spacedBy(8.dp)
        ){
            ExposedDropdownMenuBox(expanded,onExpandedChange={expanded=!expanded}){
                OutlinedTextField(
                    value=selected,
                    onValueChange={},
                    readOnly=true,
                    label={Text("Subject")},
                    trailingIcon={Icon(if(expanded)Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown,null)},
                    modifier=Modifier.fillMaxWidth()
                )
                ExposedDropdownMenu(expanded,onDismissRequest={expanded=false}){
                    subjects.forEach{ subj->
                        DropdownMenuItem(onClick={selected=subj;expanded=false}){
                            Text(subj)
                        }
                    }
                }
            }
            Spacer(Modifier.weight(1f))
            Box(Modifier.fillMaxWidth(),contentAlignment=Alignment.Center){
                Button(onClick={nav.navigate(Routes.UPLOAD)}){Text("Next")}
            }
        }
    }
}

@Composable
fun DocumentUploadScreen(nav: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Upload Documents") },
                modifier = Modifier.statusBarsPadding()
            )
        }
    ) { inner ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(inner)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Upload your scanned ID & transcript", style = MaterialTheme.typography.h6)
            Spacer(Modifier.height(24.dp))
            Button(onClick = {
                // simulate upload
                val stud = EnrollRepository.students.lastOrNull() ?: return@Button
                EnrollRepository.documents.add(Document(stud, uri = "/fake/path/doc.pdf", verified = false))
                nav.navigate(Routes.PAYMENT)
            }, modifier = Modifier.fillMaxWidth()) {
                Text("Upload")
            }
        }
    }
}

@Composable
fun PaymentScreen(nav: NavHostController) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    var amount by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }
    val MAX = 10_000.0

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text("Fee Payment") },
                modifier = Modifier.statusBarsPadding()
            )
        }
    ) { inner ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(inner)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = amount,
                onValueChange = {
                    amount = it
                    error = null
                },
                label = { Text("Amount") },
                modifier = Modifier.fillMaxWidth(),
                isError = error != null
            )
            error?.let {
                Text(it, color = MaterialTheme.colors.error, style = MaterialTheme.typography.caption)
            }
            Spacer(Modifier.height(16.dp))
            Button(onClick = {
                val v = amount.toDoubleOrNull()
                when {
                    v == null      -> error = "Enter a valid number"
                    v > MAX        -> error = "Maximum ₱${MAX.toInt()}"
                    else           -> {
                        val stud = EnrollRepository.students.last()
                        EnrollRepository.payments.add(Payment(stud, v, paid = true))
                        scope.launch {
                            scaffoldState.snackbarHostState.showSnackbar("Paid ₱${v.toInt()} successfully!")
                        }
                        nav.navigate(Routes.STATUS)
                    }
                }
            }, modifier = Modifier.fillMaxWidth()) {
                Text("Pay & View Status")
            }
        }
    }
}

@Composable
fun StatusScreen(nav: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Application Status") },
                modifier = Modifier.statusBarsPadding()
            )
        }
    ) { inner ->
        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(inner)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(EnrollRepository.students) { stud ->
                val docs = EnrollRepository.documents.filter { it.student == stud }
                val pay  = EnrollRepository.payments.find  { it.student == stud }
                Card(Modifier.fillMaxWidth(), elevation = 4.dp) {
                    Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text("Student: ${stud.name}")
                        Text("Documents: ${docs.size}")
                        Text("Paid: ₱${pay?.amount ?: 0.0} – ${if (pay?.paid == true) "Done" else "Pending"}")
                        Text("All Verified: ${docs.all { it.verified }}")
                    }
                }
            }
        }
    }
}

