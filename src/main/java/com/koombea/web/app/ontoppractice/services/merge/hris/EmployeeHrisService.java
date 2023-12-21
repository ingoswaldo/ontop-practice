/*
 * @creator: Oswaldo Montes
 * @date: December 14, 2023
 *
 */
package com.koombea.web.app.ontoppractice.services.merge.hris;

import com.merge.api.resources.hris.employees.EmployeesClient;
import com.merge.api.resources.hris.employees.requests.EmployeesListRequest;
import com.merge.api.resources.hris.types.EmployeeRequest;
import com.merge.api.resources.hris.types.PaginatedEmployeeList;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.Optional;

@Getter
public class EmployeeHrisService extends BaseHrisService {

    private final EmployeesClient employee;

    public EmployeeHrisService(String accountToken) {
        super(accountToken);
        this.employee = getClient().employees();
    }

    public PaginatedEmployeeList getAllPaginated(Optional<String> cursor, Optional<Integer> pageSize, Optional<OffsetDateTime> modifiedAfter) {
        EmployeesListRequest.Builder employeesListRequest = EmployeesListRequest.builder()
                .cursor(cursor)
                .pageSize(pageSize.orElse(100));

        if (modifiedAfter.isPresent()) employeesListRequest.modifiedAfter(modifiedAfter);

        return getEmployee().list(employeesListRequest.build());
    }
}
