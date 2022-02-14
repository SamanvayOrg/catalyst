package org.catalysts.commengage.repository;

import org.catalysts.commengage.domain.CodedLocation;
import org.catalysts.commengage.domain.QRCode;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Repository
public interface CodedLocationRepository extends CrudRepository<CodedLocation, Integer> {
    CodedLocation findByLatAndLng(double lat, double lng);

    @QueryHints(@javax.persistence.QueryHint(name="org.hibernate.fetchSize", value="50"))
    List<CodedLocation> findAllByNumberOfTimesLookedUpOrLastModifiedDateBeforeOrderByLastModifiedDateAsc(int numberOfTimesLookedUp, Date date);
    int countAllByNumberOfTimesLookedUpOrLastModifiedDateBeforeOrderByLastModifiedDateAsc(int numberOfTimesLookedUp, Date date);

    default List<CodedLocation> getNearExpiringAndNewLocations(int cacheDays) {
        Date date = getDate(cacheDays);
        return findAllByNumberOfTimesLookedUpOrLastModifiedDateBeforeOrderByLastModifiedDateAsc(0, date);
    }

    private Date getDate(int cacheDays) {
        LocalDate localDate = LocalDate.now().minusDays(cacheDays);
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    default int countNearExpiringAndNewLocations(int cacheDays) {
        Date date = getDate(cacheDays);
        return countAllByNumberOfTimesLookedUpOrLastModifiedDateBeforeOrderByLastModifiedDateAsc(0, date);
    }

    int countAllBy();
    int countAllByNumberOfTimesLookedUpEquals(int numberOfTimesLookedUp);
}
