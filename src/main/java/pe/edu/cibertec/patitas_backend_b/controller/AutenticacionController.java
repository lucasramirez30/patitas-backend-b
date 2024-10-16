package pe.edu.cibertec.patitas_backend_b.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.cibertec.patitas_backend_b.dto.LoginRequestDTO;
import pe.edu.cibertec.patitas_backend_b.dto.LoginResponseDTO;
import pe.edu.cibertec.patitas_backend_b.dto.LogoutRequestDTO;
import pe.edu.cibertec.patitas_backend_b.dto.LogoutResponseDTO;
import pe.edu.cibertec.patitas_backend_b.service.AutenticacionService;
import pe.edu.cibertec.patitas_backend_b.service.SalidaService;

import java.time.Duration;
import java.util.Arrays;

@RestController
@RequestMapping("/autenticacion")
public class AutenticacionController {

    @Autowired
    AutenticacionService autenticacionService;

    @Autowired
    SalidaService salidaService;

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginRequestDTO loginRequestDTO){

        try {
            Thread.sleep(Duration.ofSeconds(5));

            String[] datosUsuario = autenticacionService.validarUsuario(loginRequestDTO);
            System.out.println("Resultado: " + Arrays.toString(datosUsuario));

            if(datosUsuario == null){
                return new LoginResponseDTO("01", "Error: Usuario no encontrado", "", "");
            }

            return new LoginResponseDTO("00", "", datosUsuario[0], datosUsuario[1]);

        } catch (Exception e) {
            return new LoginResponseDTO("99", "Error: Ocurrio un problema", "", "");
        }
    }

    @PostMapping("/logout")
    public LogoutResponseDTO logout(@RequestBody LogoutRequestDTO logoutRequestDTO){
        System.out.println(logoutRequestDTO);
        try {
            Thread.sleep(Duration.ofSeconds(5));

            if (logoutRequestDTO == null) {
                return new LogoutResponseDTO("99", "Error: al guardar la auditoria" + logoutRequestDTO);
            }

            salidaService.registrarSalida(logoutRequestDTO.nombreUsuario());

            return new LogoutResponseDTO("00", "Sesion cerrada correctamente");
        } catch (Exception e) {
            return new LogoutResponseDTO("99", "Error: Ocurrio un problema");
        }
    }
}
