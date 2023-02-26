package fr.mspr.webshop.utils.mapper;

import fr.mspr.webshop.data.dto.Adress;
import fr.mspr.webshop.data.dto.Company;
import fr.mspr.webshop.data.dto.CustomerDTO;
import fr.mspr.webshop.data.model.Profile;

import java.util.ArrayList;

public class ProfileMapper {

     public static CustomerDTO toCustomerDTO(Profile profile) {
              return CustomerDTO.builder()
                     .id(profile.getId())
                     .name(profile.getFirstName() + " " + profile.getLastName())
                     .username(profile.getUsername())
                     .firstName(profile.getFirstName())
                     .lastName(profile.getLastName())
                     .address(Adress.builder()
                             .city(profile.getCity())
                             .postalCode(profile.getPostalCode())
                             .build())
                     .company(Company.builder()
                             .companyName(profile.getCompanyName())
                             .build())
                     .orders(new ArrayList<>(profile.getOrders()))
                     .createdAt(profile.getCreatedAt())
                     .build();

     }


 }
