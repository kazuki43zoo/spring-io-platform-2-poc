package com.example.app.bodyadvice;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.terasoluna.gfw.common.exception.ResourceNotFoundException;
import org.terasoluna.gfw.common.message.ResultMessages;

import javax.validation.groups.Default;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@RequestMapping("rest/bodyadvice/accounts")
@RestController
public class BodyAdviceRestController {

    private Map<String, AccountResource> accounts = new ConcurrentHashMap<>();

    IdGenerator idGenerator = new IdGenerator() {
    };

    @RequestMapping(method = RequestMethod.GET)
    @JsonView(AccountResource.View.class)
    public List<AccountResource> getAccounts() {
        return new ArrayList<>(accounts.values());
    }

    @RequestMapping(method = RequestMethod.POST)
    @JsonView(AccountResource.View.class)
    @ResponseStatus(HttpStatus.CREATED)
    public AccountResource postAccount(
            @JsonView(AccountResource.View.class)
            @RequestBody
            @Validated({Default.class, AccountResource.Post.class})
            AccountResource newResource) {
        newResource.setId(idGenerator.generate());
        accounts.put(newResource.getId(), newResource);
        return newResource;
    }

    @RequestMapping(path = "{id}", method = RequestMethod.GET)
    @JsonView(AccountResource.View.class)
    public AccountResource getAccount(@PathVariable("id") String id) {
        AccountResource accountResource = accounts.get(id);
        if (accountResource == null) {
            throw new ResourceNotFoundException("Account does not found. id : " + id);
        }
        return accountResource;
    }

    @RequestMapping(path = "{id}", method = RequestMethod.PUT)
    @JsonView(AccountResource.View.class)
    public AccountResource putAccount(
            @PathVariable("id") String id,
            @JsonView(AccountResource.View.class)
            @RequestBody
            @Validated({Default.class, AccountResource.Put.class})
            AccountResource updateResource) {
        getAccount(id);
        accounts.put(id, updateResource);
        return updateResource;
    }

    @RequestMapping(path = "{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccount(@PathVariable("id") String id) {
        accounts.remove(id);
    }

    @RequestMapping(path = "format", method = RequestMethod.GET)
    @JsonView(AccountResource.View.class)
    public AccountResource getAccountFormat() {
        return createDummyAccountResource(null);
    }


    @RequestMapping(path = "{id}/personName", method = RequestMethod.GET)
    @JsonView(PersonName.View.class)
    public AccountResource getPersonName(@PathVariable("id") String id) {
        return getAccount(id);
    }


    @RequestMapping(path = "{id}/personName", method = RequestMethod.PUT)
    @JsonView(PersonName.View.class)
    public AccountResource putPersonName(
            @PathVariable("id") String id,
            @JsonView(PersonName.View.class)
            @RequestBody
            @Validated({Default.class, PersonName.Put.class})
            AccountResource updateResource) {
        AccountResource accountResource = getAccount(id);
        accountResource.setPersonName(updateResource.getPersonName());
        return accountResource;
    }

    @RequestMapping(path = "{id}/streetAddress", method = RequestMethod.GET)
    @JsonView(StreetAddress.View.class)
    public AccountResource getStreetAddress(@PathVariable("id") String id) {
        return getAccount(id);
    }

    @RequestMapping(path = "{id}/streetAddress", method = RequestMethod.PUT)
    @JsonView(StreetAddress.View.class)
    public AccountResource putStreetAddress(
            @PathVariable("id") String id,
            @JsonView(StreetAddress.View.class)
            @RequestBody
            @Validated({Default.class, StreetAddress.Put.class})
            AccountResource updateResource) {
        AccountResource accountResource = getAccount(id);
        accountResource.setStreetAddress(updateResource.getStreetAddress());
        return accountResource;
    }

    @RequestMapping(path = "{id}/mailAddress", method = RequestMethod.GET)
    @JsonView(MailAddress.View.class)
    public AccountResource getMailAddress(@PathVariable("id") String id) {
        return getAccount(id);
    }

    @RequestMapping(path = "{id}/mailAddress", method = RequestMethod.PUT)
    @JsonView(MailAddress.View.class)
    public AccountResource putMailAddress(
            @PathVariable("id") String id,
            @JsonView(MailAddress.View.class)
            @RequestBody
            @Validated({Default.class, MailAddress.Put.class})
            AccountResource updateResource) {
        AccountResource accountResource = getAccount(id);
        accountResource.setMailAddress(updateResource.getMailAddress());
        return accountResource;
    }

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, ResultMessages> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ResultMessages messages = ResultMessages.error();
        e.getBindingResult().getAllErrors().forEach(error -> messages.add(error.getCode(), error.getArguments()));
        return Collections.singletonMap("message", messages);

    }

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, ResultMessages> handleResourceNotFoundException(ResourceNotFoundException e) {
        return Collections.singletonMap("message", e.getResultMessages());
    }

    private AccountResource createDummyAccountResource(String id) {
        return AccountResource.Builder.anAccountResource()
                .withId(id)
                .withPersonName(
                        PersonName.Builder.aPersonName()
                                .withFirst("Kazuki")
                                .withLast("Shimizu")
                                .build())
                .withStreetAddress(
                        StreetAddress.Builder.aStreetAddress()
                                .withZipCode("123-4567")
                                .withPrefecture("Tokyo")
                                .withCity("Toshima-ku")
                                .withAddress1("Nagasaki")
                                .withAddress2("1-2-3")
                                .withAddress3("Yamada-sou 101").build())
                .withMailAddress(
                        MailAddress.Builder.aMailAddress()
                                .withAddress("test@example.com")
                                .withPublication(true)
                                .build())
                .build();
    }

    public void setIdGenerator(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    public interface IdGenerator {
        default String generate() {
            return UUID.randomUUID().toString();
        }
    }

}
