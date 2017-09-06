package com.mmonti.view.model.providers;

import com.mmonti.view.model.Address;
import com.mmonti.view.model.CustomAddress;
import com.mmonti.view.model.Person;

import java.util.List;

public interface AddressProvider extends Provider<Person, Address, CustomAddress> {

    List<String> one(Address address);
    List<String> two(Address address);

}
