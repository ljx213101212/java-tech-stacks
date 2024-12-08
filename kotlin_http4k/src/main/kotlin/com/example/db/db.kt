import com.example.model.Users
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun initDatabase() {
    Database.connect(
        url = System.getProperty("db.url", "jdbc:mysql://localhost:6603/kotlin_demo"),
        driver = "com.mysql.cj.jdbc.Driver",
        user = System.getProperty("db.user", "root"),
        password = System.getProperty("db.password", "root")
    )
    transaction {
        SchemaUtils.create(Users)
    }
}
