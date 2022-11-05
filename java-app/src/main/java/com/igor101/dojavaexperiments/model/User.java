package com.igor101.dojavaexperiments.model;

import java.util.Collection;

public record User(long id,
                   String name,
                   String email,
                   Collection<UserRole> roles) {
}
