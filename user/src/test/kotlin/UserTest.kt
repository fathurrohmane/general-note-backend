import com.elkusnandi.common.entity.Role
import com.elkusnandi.common.entity.Users
import com.elkusnandi.common.repository.RoleRepository
import com.elkusnandi.common.repository.UserRepository
import com.elkusnandi.common.repository.UserTokenRepository
import com.elkusnandi.common.request.RegisterRequest
import com.elkusnandi.common.service.UserServiceImpl
import org.apache.coyote.BadRequestException
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.argumentCaptor
import org.springframework.security.crypto.password.PasswordEncoder
import kotlin.test.Test
import kotlin.test.assertEquals


@ExtendWith(MockitoExtension::class)
class UserServiceTest {

    @Mock
    lateinit var passwordEncoder: PasswordEncoder

    @Mock
    lateinit var userRepository: UserRepository

    @Mock
    lateinit var roleRepository: RoleRepository

    @Mock
    lateinit var userTokenRepository: UserTokenRepository

    @InjectMocks
    lateinit var userService: UserServiceImpl

    @Test
    fun getUsers() {
        val user = Users(0L, "admin", "{noop}123456")
        user.roles = mutableSetOf(Role(0L, "ADMIN"), Role(1L, "USER"))

        `when`(userRepository.findAll()).thenReturn(arrayListOf(user))

        val result = userService.getAllUsers()

        assertEquals(0L, result[0].id)
        assertEquals("admin", result[0].userName)
        assertEquals("{noop}123456", result[0].password)
        assertEquals(2, result[0].roles.size)
    }

    @Test
    fun testRegister_withExistingUser_returnFailed() {
        val username = "admin"

        `when`(userRepository.existsByUserName(username)).thenReturn(true)

        assertThrows(BadRequestException::class.java) {
            userService.register(
                RegisterRequest(
                    userName = username,
                    password = "123456"
                )
            )
        }
    }

    @Test
    fun `should trim and lowercase username before saving`() {
        // Arrange
        val request = RegisterRequest(userName = "  NewUser  ", password = "pass")
        val userRole = Role(id = 1L, name = "user")

        `when`(userRepository.existsByUserName("newuser")).thenReturn(false)
        `when`(roleRepository.findByName("admin")).thenReturn(null)
        `when`(roleRepository.findByName("user")).thenReturn(userRole)
        `when`(passwordEncoder.encode("pass")).thenReturn("encodedPass")

        val userCaptor = argumentCaptor<Users>()
        `when`(userRepository.save(userCaptor.capture())).thenReturn(
            Users(id = 1L, userName = "newuser", password = "encodedPass")
        )

        // Act
        userService.register(request)

        // Assert
        val savedUser = userCaptor.firstValue
        assertEquals("newuser", savedUser.userName)  // ✅ Assert the logic happened before save
    }

}