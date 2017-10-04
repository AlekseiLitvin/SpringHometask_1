package by.epam.aliaksei.litvin.service.impl;

import by.epam.aliaksei.litvin.domain.Auditorium;
import by.epam.aliaksei.litvin.service.AuditoriumService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class AuditoriumServiceImpl implements AuditoriumService {

    private static final Logger LOGGER = Logger.getLogger(AuditoriumServiceImpl.class.getName());

    private Set<Auditorium> auditoriums;
    private String auditoriumsDataFilename;

    private static final String PARAMETERS_DELIMITER = ";";
    private static final String VIP_SEATS_DELIMITER = ",";
    private static final int NAME_VALUE_INDEX = 0;
    private static final int NUMBER_OF_SEATS_VALUE_INDEX = 1;
    private static final int VIP_SEATS_VALUE_INDEX = 2;

    public AuditoriumServiceImpl(String auditoriumsDataFilename) {
        this.auditoriumsDataFilename = auditoriumsDataFilename;
    }

    private void init() {
        try (Scanner sc = new Scanner(new File(auditoriumsDataFilename))) {
            auditoriums = new HashSet<>();
            while (sc.hasNextLine()) {
                String[] params = sc.nextLine().split(PARAMETERS_DELIMITER);
                Auditorium auditorium = new Auditorium();
                auditorium.setName(params[NAME_VALUE_INDEX]);
                auditorium.setNumberOfSeats(Long.valueOf(params[NUMBER_OF_SEATS_VALUE_INDEX]));
                String[] vipSeats = params[VIP_SEATS_VALUE_INDEX].split(VIP_SEATS_DELIMITER);
                Set<Long> vipSeatsSet = Arrays.stream(vipSeats).mapToLong(Long::parseLong).boxed().collect(Collectors.toSet());
                auditorium.setVipSeats(new HashSet<>(vipSeatsSet));
                auditoriums.add(auditorium);
            }
        } catch (FileNotFoundException e) {
            LOGGER.info(e.getMessage());
        }
    }

    @Nonnull
    @Override
    public Set<Auditorium> getAll() {
        return auditoriums;
    }

    @Nullable
    @Override
    public Auditorium getByName(@Nonnull String name) {
        return auditoriums.stream()
                .filter(auditorium -> auditorium.getName().equals(name))
                .findFirst()
                .orElse(null);
    }
}
