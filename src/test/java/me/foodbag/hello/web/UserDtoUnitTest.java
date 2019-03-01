package me.foodbag.hello.web;

import me.foodbag.hello.persistence.model.User;
import me.foodbag.hello.web.dto.UserDto;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

public class UserDtoUnitTest {

    private ModelMapper modelMapper = new ModelMapper();

    @Test
    public void whenConvertUserEntity_dtoShouldBeCorrect() {
        User user = new User();
        user.setId(1);
        user.setFirstName("Jack");
        user.setLastName("Jackson");
        user.setEmail("Jack@Jackson.com");
        user.setPassword("Dummypass");

        UserDto userDto = modelMapper.map(user, UserDto.class);
        Assert.assertEquals(user.getFirstName(), userDto.getFirstName());
        Assert.assertEquals(user.getLastName(), userDto.getLastName());
        Assert.assertEquals(user.getEmail(), userDto.getEmail());
        Assert.assertEquals(user.getPassword(), userDto.getPassword());
    }
}
