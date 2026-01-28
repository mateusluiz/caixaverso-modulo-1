package io.github.mateusluiz.finances.api.resource;

import io.github.mateusluiz.finances.app.dto.TransactionCreateCommand;
import io.github.mateusluiz.finances.app.dto.TransactionResult;
import io.github.mateusluiz.finances.app.usecase.CreateTransactionUseCase;
import io.github.mateusluiz.finances.app.usecase.ListTransactionsUseCase;
import io.github.mateusluiz.finances.shared.Auth;
import jakarta.validation.Valid;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

import java.time.YearMonth;
import java.util.List;

@Path("/api/v1/transactions")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TransactionResource {

    private final CreateTransactionUseCase createTransactionUseCase;
    private final ListTransactionsUseCase listTransactionsUseCase;

    public TransactionResource(CreateTransactionUseCase createTransactionUseCase,
                               ListTransactionsUseCase listTransactionsUseCase) {
        this.createTransactionUseCase = createTransactionUseCase;
        this.listTransactionsUseCase = listTransactionsUseCase;
    }

    @POST
    public TransactionResult create(@Valid TransactionCreateCommand cmd) {
        return createTransactionUseCase.execute(Auth.USER_ID, cmd);
    }

    @GET
    public List<TransactionResult> list(@QueryParam("month") String month) {
        if (month == null || month.isBlank()) {
            throw new BadRequestException("Parâmetro 'month' é obrigatório no formato YYYY-MM");
        }

        YearMonth ym;
        try {
            ym = YearMonth.parse(month);
        } catch (Exception e) {
            throw new BadRequestException("Formato inválido. Use YYYY-MM, ex: 2026-01");
        }

        return listTransactionsUseCase.execute(Auth.USER_ID, ym);
    }
}
