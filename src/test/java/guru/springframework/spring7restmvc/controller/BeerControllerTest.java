package guru.springframework.spring7restmvc.controller;

import guru.springframework.spring7restmvc.model.BeerDTO;
import guru.springframework.spring7restmvc.services.BeerService;
import guru.springframework.spring7restmvc.services.BeerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import tools.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    BeerService beerService;

    BeerServiceImpl beerServiceImpl;

    public static final String USERNAME = "user1";
    public static final String PASSWORD = "password";


    @BeforeEach
    void setUp() {
        beerServiceImpl = new BeerServiceImpl();
        MockitoAnnotations.openMocks(this);
    }

    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Captor
    ArgumentCaptor<BeerDTO> beerArgumentCaptor;

    //I have to do the update test method to test the @NotBlank and @NotNull


    @Test
    void testUpdateNullBear() throws Exception {
        BeerDTO beerDTO = beerServiceImpl.beerList(null, null, false, 1, 25)
                .getContent().get(0);
        beerDTO.setBeerName("");
        given(beerService.updateBeerById(any(), any())).willReturn(Optional.of(beerDTO));
        System.out.println(beerDTO);

        MvcResult updateMockMvc = mockMvc.perform(put(BeerController.BEER_PATH_ID, beerDTO.getId())
                        .with(httpBasic(USERNAME, PASSWORD))
                        .content(objectMapper.writeValueAsString(beerDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(1)))
                .andExpect(status().isBadRequest()).andReturn();

        System.out.println(updateMockMvc.getResponse().getContentAsString());
    }

    @Test
    void testCreateBeerNullBeerName() throws Exception {
        BeerDTO beerDTO = BeerDTO.builder().build();

        given(beerService.saveNewBeer(any()))
                .willReturn(beerServiceImpl
                        .beerList(null, null, false, 1, 25).getContent().get(1));



        MvcResult myMockmvc = mockMvc.perform(post(BeerController.BEER_PATH)
                        .with(httpBasic(USERNAME, PASSWORD))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beerDTO)))
                .andExpect(jsonPath("$.length()", is(6)))
                .andExpect(status().isBadRequest()).andReturn();

        System.out.println(myMockmvc.getResponse().getContentAsString());
    }

    @Test
    void getBeerIdNotFound() throws Exception {

        given(beerService.getBeerById(any(UUID.class))).willReturn(Optional.empty());
        mockMvc.perform(get(BeerController.BEER_PATH_ID, UUID.randomUUID())
                        .with(httpBasic(USERNAME, PASSWORD)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testPatchBeer() throws Exception {
        BeerDTO beer = beerServiceImpl.beerList(null, null, false, 1, 25).getContent().getFirst();

        Map<String, Object> beerMap = new HashMap<>();
        beerMap.put("beerName", "New Name");

        mockMvc.perform(patch(BeerController.BEER_PATH_ID,  beer.getId())
                        .with(httpBasic(USERNAME, PASSWORD))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(beerMap)))
                .andExpect(status().isNoContent());

        verify(beerService).patchBeerById(uuidArgumentCaptor.capture(), beerArgumentCaptor.capture());
        assertThat(beer.getId()).isEqualTo(uuidArgumentCaptor.getValue());
        assertThat(beerMap.get("beerName")).isEqualTo(beerArgumentCaptor.getValue().getBeerName());
    }

    @Test
    void testDeleteBeer() throws Exception {
        BeerDTO beer = beerServiceImpl.beerList(null, null, false, 1, 25).getContent().getFirst();

        given(beerService.deleteById(beer.getId())).willReturn(true);

        mockMvc.perform(delete(BeerController.BEER_PATH_ID,  beer.getId())
                        .with(httpBasic(USERNAME, PASSWORD))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());


        verify(beerService).deleteById(uuidArgumentCaptor.capture());
        assertThat(beer.getId()).isEqualTo(uuidArgumentCaptor.getValue());
    }

    @Test
    void testUpdateBeer() throws Exception {
        BeerDTO beer = beerServiceImpl.beerList(null, null, false, 1, 25).getContent().getFirst();

        given(beerService.updateBeerById(any(), any())).willReturn(Optional.of(beer));

        mockMvc.perform(put(BeerController.BEER_PATH_ID,  beer.getId())
                        .with(httpBasic(USERNAME, PASSWORD))
                        .content(objectMapper.writeValueAsString(beer))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(beerService).updateBeerById(uuidArgumentCaptor.capture(), beerArgumentCaptor.capture());
    }

    @Test
    void testCreateNewBeer() throws Exception {
        BeerDTO beer = beerServiceImpl.beerList(null, null, false, 1, 25).getContent().get(0);
        beer.setVersion(null);
        beer.setId(null);

        //Here I set empty any()'s because it worked. What I understand behind this behavior, since saveNewBeer(Beer)
        //expects a Beer as an argument, that any will always behave as Beer.
        given(beerService.saveNewBeer(any())).willReturn(beerServiceImpl.beerList(null, null, null, 1, 25).getContent().get(1));

        mockMvc.perform(post(BeerController.BEER_PATH)
                        .with(httpBasic(USERNAME, PASSWORD))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beer)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void testListBeers() throws Exception {

        //Here I implemented this different, so if I have any error, later in the course, that's the reason. I'll have to check the original code from the course
        Page<BeerDTO> beerList = beerServiceImpl.beerList(null, null, false, 1, 25);

        given(beerService.beerList(any(), any(), any(), any(), any()))
                .willReturn(beerList);

        mockMvc.perform(get(BeerController.BEER_PATH)
                        .with(httpBasic(USERNAME, PASSWORD))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content.length()", is(beerList.getContent().size())));

    }

    @Test
    void getBeerById() throws Exception {
        BeerDTO testBeer = beerServiceImpl.beerList(null, null, false, 1, 25).getContent().getFirst();

        given(beerService.getBeerById(testBeer.getId())).willReturn(Optional.of(testBeer));
        mockMvc.perform(get(BeerController.BEER_PATH_ID, testBeer.getId())
                        .with(httpBasic(USERNAME, PASSWORD))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(testBeer.getId().toString())))
                .andExpect(jsonPath("$.beerName", is(testBeer.getBeerName())));
    }
}