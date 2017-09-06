package com.mmonti.view.model.providers;

import com.mmonti.view.model.Address;
import com.mmonti.view.model.CustomAddress;
import com.mmonti.view.model.Person;
import com.mmonti.view.services.AddressService;

import java.util.List;

import static java.util.Arrays.asList;

public class AddressProviderImpl implements AddressProvider {

    private AddressService addressService;

    @Override
    public CustomAddress provide(final Person rootEntity, final Address target) {
        return CustomAddress.builder().one(one(target)).two(two(target)).build();
    }

    /**
     *
     * @param addressService
     */
    public AddressProviderImpl(final AddressService addressService) {
        this.addressService = addressService;
    }

    @Override
    public List<String> one(final Address target) {
        return asList(addressService.getAddressByStreet(target.getAddress()).getAddress());
    }

    @Override
    public List<String> two(final Address target) {
        return asList(addressService.getAddressByStreet(target.getAddress()).getCity());
    }
}
