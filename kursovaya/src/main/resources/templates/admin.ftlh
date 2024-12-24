<#import "blocks/template.ftlh" as t>
<@t.template user>
    <style>
        .table {
            width: 100%;
            table-layout: fixed;
            margin-bottom: 20px;
        }

        .table th, .table td {
            word-wrap: break-word;
            text-align: center;
            padding: 15px;
            vertical-align: middle;
        }
    </style>

    <h1 style="text-align: center">Панель администратора</h1><hr>
    <hr>
    <H3 style="text-align: center">Список пользователей</H3>
    <hr>
    <table class="table table-hover table-dark">
        <tr>
            <td>Email</td>
            <td>Номер телефона</td>
            <td>Активность</td>
            <td>Роли</td>
            <td>Заблокировать пользователя</td>
            <td>Подробная информация</td>
            <td>Редактирование</td>
        </tr>
        <#list users as user>
            <tr>
                <th>${user.email}</th>
                <th>${user.phoneNumber}</th>
                <th><#if user.active>Активен<#else>Забанен</#if></th>
                <th><#list user.roles as role>${role}</#list></th>
                <th><a href="/admin/user/ban/${user.id}"
                       onclick="event.preventDefault(); document.getElementById('ban-form-${user.id}').submit();">
                        Бан
                    </a>
                    <form id="ban-form-${user.id}" action="/admin/user/ban/${user.id}" method="post" style="display: none;">
                        <input type="hidden" name="_csrf" value="${_csrf.token}">
                    </form>
                </th>
                <th><a href="/admin/user/${user.id}">Подробная информация</a></th>
                <th><a href="/admin/user/edit/${user.id}">Редактировние</a></th>
            </tr>
        <#else>
            <h3 style="text-align: center">Нет данных о пользователях</h3>
        </#list>
    </table>

    <hr>
    <H3 style="text-align: center">Список товаров</H3>
    <hr>
    <table class="table table-hover table-dark">
        <tr>
            <td>Бренд</td>
            <td>Модель</td>
            <td>Описание</td>
            <td>Цена закупки (пользователя)</td>
            <td>Итоговая цена</td>
            <td>Категория</td>
            <td>Статус</td>
            <td>Продавец</td>
            <td>Дата создания</td>
            <td>Подробная информация</td>
            <td>Редактирование</td>
            <td>Удаление</td>
        </tr>
        <#list products as product>
            <tr>
                <th>${product.brand}</th>
                <th>${product.title}</th>
                <th>${product.description}</th>
                <th>${product.price}</th>
                <th>${product.magprice}</th>
                <th>${product.category}</th>
                <th>${product.status}</th>
                <th>${product.user.email}</th>
                <th>${dateUtils.format(product.dateOfCreated)}</th>
                <th><a href="/admin/product/${product.id}">Подробная информация</a></th>
                <th><a href="/admin/product/edit/${product.id}">Редактировние</a></th>
                <th>
                    <form id="delete-form-${product.id}" action="/admin/product/delete/${product.id}" method="post" style="display: inline;">
                        <input type="hidden" name="_csrf" value="${_csrf.token}">
                        <button type="submit" class="btn btn-danger btn-sm" style="width: 100%;">
                            Удалить
                        </button>
                    </form>
                </th>
            </tr>
        <#else>
            <h3 style="text-align: center">Нет данных о товарах</h3>
        </#list>
    </table>

    <hr>
    <a class="btn btn-dark mb-3" style="text-align: center; width: 100%" href="/admin/product/add">Добавить товар</a><br>
    <hr>
    <H3 style="text-align: center">Список Продаж</H3>
    <hr>
    <table class="table table-hover table-dark">
        <tr>
            <td>Данные товара</td>
            <td>Покупатель</td>
            <td>Продавец (администратор)</td>
            <td>Цена продавца (закупочная)</td>
            <td>Итоговая цена</td>
            <td>Дата продажи</td>
        </tr>
        <#list sales as sale>
            <tr>
                <th>${sale.product.brand} ${sale.product.title}</th>
                <th>${sale.user.email}</th>
                <th>${sale.saleuser_id.email}</th>
                <th>${sale.price}</th>
                <th>${sale.magprice}</th>
                <th>${dateUtils.format(sale.saleDate)}</th>
            </tr>
        <#else>
            <h3 style="text-align: center">Нет данных о продажах</h3>
        </#list>
    </table>
    <hr>
    <h3 style="text-align: center; margin-bottom: 20px;">Фильтр по дате</h3>
    <form action="/admin/exportExcel" method="get"  style = "align-items: center;">
        <label for="startDate" style="font-weight: bold;">Начало периода:</label>
        <input type="datetime-local" id="startDate" name="startDate" required>
        <label for="endDate" style="font-weight: bold;">Конец периода:</label>
        <input type="datetime-local" id="endDate" name="endDate" required>
        <button type="submit" class="btn btn-dark mb-3" style="text-align: center; width: 100%">Экспорт в Excel</button>
    </form>

</@t.template>
