<#import "blocks/template.ftlh" as t>
<@t.template user={}>
    <style>
        .center {
            text-align: center;
        }
        .error-container {
            display: flex;
            flex-direction: column;
            align-items: center;
        }
    </style>
    <h1 class="center">Ошибка 403</h1>
    <div class="error-container">
        <p>У вас нет прав доступа к этому ресурсу.</p>
        <a href="/">Вернуться на главную</a>
    </div>
</@t.template>
