package org.neposoft.reservation.controllers;

import domain.AppFacade;
import domain.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Set;


@RequestMapping("/reservations")
@RestController
public class ReservationsController {

    @Autowired
    protected AppFacade facade;

    /**
     * @api {get} /reservations/{id} Returns a reservation with given id
     * @apiName GetReservations
     * @apiGroup Reservation
     * @apiSuccess {java.util.List} Reservation All reservations in system
     */
    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Reservation getReservation(@PathVariable Integer id) {
        return facade.getReservation(id);
    }

    /**
     * @api {get} /reservations Returns all restaurants
     * @apiName GetReservations
     * @apiGroup Reservation
     * @apiSuccess {Reservation} Reservation Given reservation
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<Reservation> getReservations() {
        return facade.getAllReservations();
    }

    /**
     * @api {post} /reservations Creates a reservation
     * @apiName CreateReservation
     * @apiGroup Reservation
     * @apiSuccess Status Returns empty response with status 200 on success.
     * @apiError Status  HTTP UNPROCESSABLE ENTITY
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> putReservtion(@Valid Reservation reservation, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<String>(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        facade.addReservation(reservation);

        return new ResponseEntity<String>(HttpStatus.OK);
    }

    /**
     * @api {patch} /reservations/{id} Updates the reservation with given id : (only setting active/inactive)
     * @apiName UpdateReservation
     * @apiGroup Reservation
     * @apiSuccess Status: 200 , Returns empty response with status 200 on success.
     */
    @RequestMapping(method = RequestMethod.PATCH, value = "{id}")
    public ResponseEntity<String> updateReservation(@PathVariable Integer id) {
        facade.acceptReservation(id);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    /**
     * @api {delete} /reservations/{id}/delete Deletes the reservation with given id
     * @apiName DeleteReservation
     * @apiGroup Reservation
     * @apiSuccess Status: 200 , Returns empty response with status 200 on success.
     */
    @RequestMapping(method = RequestMethod.POST, value = "{reservation_id}/delete")
    public ResponseEntity<String> deleteReservation(@PathVariable Integer reservation_id) {
        facade.deleteReservation(reservation_id);

        return new ResponseEntity<String>(HttpStatus.OK);
    }
}
