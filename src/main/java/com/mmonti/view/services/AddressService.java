package com.mmonti.view.services;

import com.mmonti.view.model.Address;

public interface AddressService {

    Address getAddressByStreet(String streetName);

}
