package Integracao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.haroldo.minhasfinancas.api.resource.UsuarioResource;
import com.haroldo.minhasfinancas.model.entity.Usuario;
import com.haroldo.minhasfinancas.service.UsuarioService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioServiceImplTestIntegracao {
    private MockMvc mockMvc;

    @Mock
    UsuarioService usuarioService;

    @Mock
    UsuarioResource usuarioResource;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(usuarioResource).build();
    }

    @Test
    void salvarUsuarioTestIntegracao() throws Exception {
        Usuario usuario = new Usuario(null, "Layal", "nissolelo03@gmail.com", "postgres");

        ObjectMapper objectMapper = new ObjectMapper();

        String json = objectMapper.writeValueAsString(usuario);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header()
                        .string("location", Matchers.containsString("http://localhost/api/usuarios")));

    }

    @Test
    void findAllUsuariosTestIntegracao(){
        Usuario usuario = Usuario.builder()
                .nome("Layal")
                .email("nissolelo03@gmail.com")
                .senha("postgres")
                .build();

    }

}
