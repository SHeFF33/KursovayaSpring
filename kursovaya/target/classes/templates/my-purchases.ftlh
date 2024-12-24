<#import "blocks/template.ftlh" as t>
<@t.template user>
    <style>
        .card-columns {
            display: flex;
            flex-wrap: wrap;
            gap: 10px;
            margin-left: 50px;
        }

        .card {
            flex: 1 1 calc(33.333% - 20px); /* Three cards per row with gap */
            max-width: calc(33.333% - 20px);
            box-sizing: border-box;
            display: flex;
            flex-direction: column;
            justify-content: space-between;
            background-color: #333;
            color: white;
            padding: 10px;
            border-radius: 5px;
        }

        .card img {
            height: 300px;
            width: 100%;
            object-fit: contain;
            margin-bottom: 10px;
        }

        .card-body,
        .card-footer {
            text-align: center;
            padding: 5px;
        }
    </style>
    <h1 style="text-align: center">История покупок</h1>
    <hr>
    <div class="card-columns">
        <#list purchases as purchase>
            <div class="card text-white bg-dark my-3 product">
                <img src="/images/${purchase.product.previewImageId}"
                     class="card-img-top">
                <div class="card-body">
                    <h5 class="card-title">${purchase.product.brand}</h5>
                    <h5 class="card-title">${purchase.product.title}</h5>
                    <p class="card-text">${purchase.magprice} ₽</p>
                </div>
                <div class="card-footer">
                    ${purchase.product.category}
                </div>
            </div>
        <#else>
            <h1 style="text-align: center">Нет купленных товаров. Вперед за покупками!</h1>
        </#list>
    </div>
</@t.template>
