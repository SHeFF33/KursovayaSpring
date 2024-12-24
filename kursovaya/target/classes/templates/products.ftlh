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
            height: auto;
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
            width: 300px;
            object-fit: contain;
            margin-bottom: 10px;
        }

        .card-body,
        .card-footer {
            text-align: center;
            padding: 5px;
        }
    </style>
    <h1 style="text-align: center">Найдите то, что вас интересует</h1><hr>
    <form method="get" action="/">
        <div class="form-row">
            <div class="form-group col-md-2">
                <label for="searchCategory">Категория</label>
                <select id="searchCategory" name="category" class="form-control">
                    <option value="">Выберите категорию</option>
                    <option value="Телефоны" <#if category?exists && category == "Телефоны">selected</#if>>Телефоны</option>
                    <option value="Планшеты" <#if category?exists && category == "Планшеты">selected</#if>>Планшеты</option>
                    <option value="Зарядные устройства" <#if category?exists && category == "Зарядные устройства">selected</#if>>Зарядные устройства</option>
                    <option value="Наушники" <#if category?exists && category == "Наушники">selected</#if>>Наушники</option>
                    <option value="Чехлы" <#if category?exists && category == "Чехлы">selected</#if>>Чехлы</option>
                    <option value="Защитные стекла" <#if category?exists && category == "Защитные стекла">selected</#if>>Защитные стекла</option>
                    <option value="Пр. аксессуары" <#if category?exists && category == "Пр. аксессуары">selected</#if>>Пр. аксессуары</option>
                </select>
            </div>
            <div class="form-group col-md-10">
                <label for="searchWord">Поиск по объявлениям</label>
                <input type="text" name="searchWord" <#if searchWord??>value="${searchWord}"</#if> class="form-control"
                       id="searchWord">
            </div>
            <button style="width: 100%" type="submit" class="btn btn-dark">Поиск</button>
        </div>
    </form>
    <hr>
    <div class="card-columns">
        <#list products as product>
            <a href="/product/${product.id}" class="product-link">
                <div class="card text-white bg-dark my-3 product">
                    <img src="/images/${product.previewImageId}" class="card-img-top">
                    <div class="card-body">
                        <h5 class="card-title">${product.brand}</h5>
                        <h5 class="card-title">${product.title}</h5>
                        <p class="card-text">${product.magprice} ₽</p>
                    </div>
                    <div class="card-footer">
                        ${product.category}
                    </div>
                </div>
            </a>
        <#else>
            <h1 style="text-align: center">Не найдено</h1>
        </#list>
    </div>
</@t.template>
