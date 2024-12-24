<#import "blocks/template.ftlh" as t>
<@t.template user>
    <style>
        .center {
            text-align: center;
        }
        .form-container {
            display: flex;
            flex-direction: column;
            align-items: center;
        }
        .form-group {
            margin-bottom: 15px;
        }
    </style>
    <h1 class="center">Редактирование пользователя ${user.name}</h1><hr>
    <div class="form-container">
        <form action="/admin/user/edit" method="post">
            <#list roles as role>
                <div class="form-group">
                    <label>
                        <input type="checkbox" name="${role}" ${user.roles?seq_contains(role)?string("checked", "")}>
                        ${role}
                    </label>
                </div>
            </#list>
            <input type="hidden" value="${user.id}" name="userId">
            <input type="hidden" value="${_csrf.token}" name="_csrf">
            <button type="submit" class="btn btn-dark">Сохранить</button>
        </form>
    </div>
</@t.template>
