<#import 'blocks/template.ftlh' as t>
<@t.template user>
    <h1 style="text-align: center">Регистрация</h1>
    <hr>
    <form action="/registration" method="post">
        <div class="form-group">
            <label for="name">Имя пользователя</label>
            <input type="text" class="form-control" required id="name" name="name">
        </div>
        <div class="form-group">
            <label for="email">Электронная почта</label>
            <input type="email" class="form-control" id="email" required name="email">
        </div>
        <div class="form-group">
            <label for="phoneNumber">Номер телефона</label>
            <input type="tel" class="form-control" id="phoneNumber" required name="phoneNumber" pattern="\+7\(\d{3}\)\d{3}-\d{2}-\d{2}">
        </div>
        <div class="form-group">
            <label for="password">Пароль</label>
            <input type="password" class="form-control" id="password" required name="password" minlength="8">
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <button type="submit" style="width: 100%" class="btn btn-dark">Зарегистрироваться</button>
    </form>
    <#if errorMessage??>
        <h2 style="color: red">${errorMessage}</h2>
    </#if>
    <#if message??>
        <h2 style="color: green">${message}</h2>
    </#if>
</@t.template>

<script>
    document.addEventListener('DOMContentLoaded', function() {
        const phoneInput = document.getElementById('phoneNumber');
        const passwordInput = document.getElementById('password');
        const form = document.querySelector('form');

        form.addEventListener('submit', function(event) {
            const phonePattern = /^\+7\(\d{3}\)\d{3}-\d{2}-\d{2}$/;
            const phoneValue = phoneInput.value;
            const passwordValue = passwordInput.value;

            if (!phonePattern.test(phoneValue)) {
                event.preventDefault();
                alert('Неправильный формат номера телефона. Пожалуйста, введите номер в формате +7(***)***-**-**.');
                return;
            }

            if (passwordValue.length < 8) {
                event.preventDefault();
                alert('Пароль должен содержать не менее 8 символов.');
                return;
            }
        });
    });
</script>