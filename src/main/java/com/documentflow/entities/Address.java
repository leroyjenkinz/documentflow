package com.documentflow.entities;

import com.documentflow.entities.dto.ContragentDtoAddress;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.math.NumberUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "addresses")
public class Address implements Serializable {
    private static final long serialVersionUID = 2600335316131008220L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "post_index")
    private Integer index;

    @Column(name = "country")
    private String country;

    @Column(name = "city")
    private String city;

    @Column(name = "street")
    private String street;

    @Column(name = "house_number")
    private String houseNumber;

    @Column(name = "apartrment_number")
    private String apartmentNumber;

    public Address(Long id, Integer postIndex, String country, String city, String street, String houseNumber, String apartmentNumber) {
        this.id = id;
        this.index = postIndex;
        this.country = country;
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;
        this.apartmentNumber = apartmentNumber;
    }

    public Address(@NonNull ContragentDtoAddress contragentDtoAddress) {
        this(
                null,
                NumberUtils.toInt(contragentDtoAddress.getPostIndex()),
                ObjectUtils.isEmpty(contragentDtoAddress.getCountry()) ? null : contragentDtoAddress.getCountry().toUpperCase(),
                ObjectUtils.isEmpty(contragentDtoAddress.getCity()) ? null : contragentDtoAddress.getCity(),
                ObjectUtils.isEmpty(contragentDtoAddress.getStreet()) ? null : contragentDtoAddress.getStreet(),
                contragentDtoAddress.getHouseNumber(),
                contragentDtoAddress.getApartrmentNumber()
        );
    }

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "contragents",
            joinColumns = @JoinColumn(name = "address_id"),
            inverseJoinColumns = @JoinColumn(name = "organiztion_id"))
    private List<Organization> organizations = new ArrayList<>();

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "contragents",
            joinColumns = @JoinColumn(name = "address_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id"))
    private List<Person> persons = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "address", fetch = FetchType.LAZY)
    private List<Contragent> contragents = new ArrayList<>();
}