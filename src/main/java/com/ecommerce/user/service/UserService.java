package com.ecommerce.user.service;

import com.ecommerce.user.dto.AddressDTO;
import com.ecommerce.user.dto.UserRequest;
import com.ecommerce.user.dto.UserResponse;
import com.ecommerce.user.model.Address;
import com.ecommerce.user.model.User;
import com.ecommerce.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
//    private final List<User> userList = new ArrayList<>();
//    private Long NextId = 1L;

    public List<UserResponse> fetchAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }

    public void addUser(UserRequest userRequest) {
//        user.setId(NextId++);
        User user = new User();
        updateUserFromRequest(user, userRequest);
        userRepository.save(user);
    }

    public Optional<UserResponse> fetchUser(String id) {
//        for (User user : userList) {
//            if (user.getId().equals(id)) {
//                return user;
//            }
//        }
//        return null;

        return userRepository.findById(id).map(this::mapToUserResponse);
    }

    private void updateUserFromRequest(User user, UserRequest userRequest) {
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPhone(userRequest.getPhone());

        user.setAddress(mapToAddress(userRequest.getAddress()));
    }

    public boolean updateUser(String id, UserRequest userRequest) {
        return userRepository.findById(id).map(existingUser -> {
            updateUserFromRequest(existingUser, userRequest);
            userRepository.save(existingUser);
            return true;
        }).orElse(false);
    }

    private UserResponse mapToUserResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(String.valueOf(user.getId()));
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());
        response.setRole(user.getRole());

        response.setAddress(mapToAddressDTO(user.getAddress()));

        return response;
    }

    private Address mapToAddress(AddressDTO req) {
        if (req == null) return null;

        Address address = new Address();
        address.setStreet(req.getStreet());
        address.setCity(req.getCity());
        address.setState(req.getState());
        address.setCountry(req.getCountry());
        address.setZipcode(req.getZipcode());

        return address;
    }

    private AddressDTO mapToAddressDTO(Address address) {
        if (address == null) return null;

        AddressDTO dto = new AddressDTO();
        dto.setStreet(address.getStreet());
        dto.setCity(address.getCity());
        dto.setState(address.getState());
        dto.setCountry(address.getCountry());
        dto.setZipcode(address.getZipcode());

        return dto;
    }
}
