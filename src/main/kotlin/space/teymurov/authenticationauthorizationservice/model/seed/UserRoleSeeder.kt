package space.teymurov.authenticationauthorizationservice.model.seed

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import space.teymurov.authenticationauthorizationservice.model.entity.Role
import space.teymurov.authenticationauthorizationservice.model.entity.User
import space.teymurov.authenticationauthorizationservice.model.repository.RoleRepository
import space.teymurov.authenticationauthorizationservice.model.repository.UserRepository
import space.teymurov.authenticationauthorizationservice.util.Constant.ROLE_ADMIN
import space.teymurov.authenticationauthorizationservice.util.Constant.ROLE_ORGANIZATION
import space.teymurov.authenticationauthorizationservice.util.Constant.ROLE_USER

@Component
class UserRoleSeeder(
    val userRepository: UserRepository,
    val roleRepository: RoleRepository,
    val passwordEncoder: PasswordEncoder
): ApplicationRunner {
    override fun run(args: ApplicationArguments?) {
        val roleAdmin = Role(name = ROLE_ADMIN)
        if (!roleRepository.findByName(name = ROLE_ADMIN).isPresent){
            roleRepository.save(roleAdmin)
        }

        val roleUser = Role(name = ROLE_USER)
        if (!roleRepository.findByName(name = ROLE_USER).isPresent){
            roleRepository.save(roleUser)
        }

        val roleOrganization = Role(name = ROLE_ORGANIZATION)
        if (!roleRepository.findByName(name = ROLE_ORGANIZATION).isPresent) {
            roleRepository.save(roleOrganization)
        }

        val admin = User(
            name = "Admin",
            surname = "Adminov",
            patronymic = "Adminovich",
            edu = "MEPHI",
            email = "admin@admin.com",
            password = passwordEncoder.encode("admin123")
        )

        if (userRepository.findByEmail(email = admin.email) == null){
            userRepository.save(admin)
            admin.roles.add(roleAdmin)
            userRepository.save(admin)
        }
    }
}