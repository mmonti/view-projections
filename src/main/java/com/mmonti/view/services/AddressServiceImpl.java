package com.mmonti.view.services;

import com.mmonti.view.model.Address;

public class AddressServiceImpl implements AddressService {
    @Override
    public Address getAddressByStreet(String streetName) {
        return Address.builder().address(streetName).city("Glendale").state("California").build();
    }
}
