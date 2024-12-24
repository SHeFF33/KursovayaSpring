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
    <h1 style="text-align: center">История продаж</h1>
    <hr>
    <a class="btn btn-dark mt-3 mb-3" style="text-align: center; width: 100%"
       data-toggle="collapse" href="#collapseExample" role="button"
       aria-expanded="true" aria-controls="collapseExample">
        Продать свое устройство
    </a>
    <div class="collapse mb-3" id="collapseExample" style="">
        <form action="/product/create" method="post" enctype="multipart/form-data">
            <div class="form-group">
                <label for="productBrand">Укажите производителя</label>
                <select id="productBrand" required name="brand" class="form-control">
                    <option value="">Выберите производителя</option>
                    <option value="Apple">Apple</option>
                    <option value="Samsung">Samsung</option>
                    <option value="Huawei">Huawei</option>
                    <option value="Honor">Honor</option>
                    <option value="Xiaomi">Xiaomi</option>
                    <option value="Redmi">Redmi</option>
                    <option value="Oppo">Oppo</option>
                    <option value="Vivo">Vivo</option>
                    <option value="Realme">Realme</option>
                    <option value="Nothing">Nothing</option>
                    <option value="Google">Google</option>
                </select>
            </div>
            <div class="form-group">
                <label for="productName">Укажите модель</label>
                <input type="text" required class="form-control"
                       id="productName" name="title">
            </div>
            <div class="form-group">
                <label for="price">Укажите цену (иготовая цена будет рассчитана автоматически)</label>
                <div class="input-group mb-3">
                    <input type="number" required class="form-control" id="price" name="price" min="1">
                    <div class="input-group-append">
                        <span class="input-group-text">₽</span>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label for="productCategory">Укажите категорию</label>
                <select id="productCategory" required name="category" class="form-control">
                    <option value="">Выберите категорию</option>
                    <option value="Телефоны">Телефоны</option>
                    <option value="Планшеты">Планшеты</option>
                </select>
            </div>
            <div class="form-group">
                <label for="description">Добавьте описание</label>
                <textarea required class="form-control" id="description" name="description"></textarea>
            </div>
            <div class="form-group">
                <div class="custom-file">
                    <input required type="file" name="file1" id="file1">
                    <label class="custom-file-label" for="file1">Добавьте первое фото</label>
                </div>
            </div>
            <div class="form-group">
                <div class="custom-file">
                    <input type="file" name="file2" id="file2">
                    <label class="custom-file-label" for="file2">Добавьте второе фото</label>
                </div>
            </div>
            <div class="form-group">
                <div class="custom-file">
                    <input type="file" name="file3" id="file3">
                    <label class="custom-file-label" for="file3">Добавьте третье фото</label>
                </div>
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <input type="hidden" name="status" value="В продаже" />

            <button type="submit" style="width: 100%" class="btn btn-dark">Продать</button>
        </form>
    </div>
    <div class="card-columns">
        <#list products as product>
            <a href="/myproduct/${product.id}" class="product-link">
                <div class="card text-white bg-dark my-3 product">
                    <#if product.status == "Продан">
                        <div class="sold-banner" style="background-color: #333; color: white; padding: 5px; text-align: center; font-weight: bold;">
                            Продан
                        </div>
                    </#if>
                    <img src="/images/${product.previewImageId}"
                         class="card-img-top">
                    <div class="card-body">
                        <h5 class="card-title">${product.brand}</h5>
                        <h5 class="card-title">${product.title}</h5>
                        <p class="card-text">${product.price} ₽</p>
                    </div>
                    <div class="card-footer">
                        ${product.category}
                    </div>
                </div>
            </a>
        <#else>
            <h1 style="text-align: center">Нет товаров</h1>
        </#list>
    </div>
</@t.template>