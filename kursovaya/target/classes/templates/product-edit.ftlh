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
    <h1 class="center">Редактирование ${product.title}</h1><hr>
    <div class="form-container">
        <form action="/admin/product/edit" method="post" enctype="multipart/form-data">
            <div class="form-group">
                <label for="productBrand">Производитель</label>
                <select id="productBrand" required name="brand" class="form-control">
                    <option value="">Выберите производителя</option>
                    <option value="Apple" <#if product.brand == 'Apple'>selected</#if>>Apple</option>
                    <option value="Samsung" <#if product.brand == 'Samsung'>selected</#if>>Samsung</option>
                    <option value="Huawei" <#if product.brand == 'Huawei'>selected</#if>>Huawei</option>
                    <option value="Honor" <#if product.brand == 'Honor'>selected</#if>>Honor</option>
                    <option value="Xiaomi" <#if product.brand == 'Xiaomi'>selected</#if>>Xiaomi</option>
                    <option value="Redmi" <#if product.brand == 'Redmi'>selected</#if>>Redmi</option>
                    <option value="Oppo" <#if product.brand == 'Oppo'>selected</#if>>Oppo</option>
                    <option value="Vivo" <#if product.brand == 'Vivo'>selected</#if>>Vivo</option>
                    <option value="Realme" <#if product.brand == 'Realme'>selected</#if>>Realme</option>
                    <option value="Nothing" <#if product.brand == 'Nothing'>selected</#if>>Nothing</option>
                    <option value="Google" <#if product.brand == 'Google'>selected</#if>>Google</option>
                    <option value="Hoco" <#if product.brand == 'Hoco'>selected</#if>>Hoco</option>
                    <option value="Baseus" <#if product.brand == 'Baseus'>selected</#if>>Baseus</option>
                    <option value="TWS" <#if product.brand == 'TWS'>selected</#if>>TWS</option>
                    <option value="JBL" <#if product.brand == 'JBL'>selected</#if>>JBL</option>
                    <option value="Vais" <#if product.brand == 'Vais'>selected</#if>>Vais</option>
                    <option value="Defender" <#if product.brand == 'Defender'>selected</#if>>Defender</option>
                    <option value="Пр. производители" <#if product.brand == 'Пр. производители'>selected</#if>>Пр. производители</option>
                </select>
            </div>
            <div class="form-group">
                <label for="productName">Название</label>
                <input type="text" required class="form-control" id="productName" name="title" value="${product.title}">
            </div>
            <div class="form-group">
                <label for="price">Закупочная цена (итоговая цена рассчитывается автоматически)</label>
                <div class="input-group mb-3">
                    <input type="number" required class="form-control" id="price" name="price" value="${product.price}" min="1">
                    <div class="input-group-append">
                        <span class="input-group-text">₽</span>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label for="description">Описание</label>
                <textarea required class="form-control" id="description" name="description">${product.description}</textarea>
            </div>
            <div class="form-group">
                <div class="custom-file">
                    <input type="file" name="file1" id="file1">
                    <label class="custom-file-label" for="file1">Добавить первое фото</label>
                </div>
            </div>
            <div class="form-group">
                <div class="custom-file">
                    <input type="file" name="file2" id="file2">
                    <label class="custom-file-label" for="file2">Добавить второе фото</label>
                </div>
            </div>
            <div class="form-group">
                <div class="custom-file">
                    <input type="file" name="file3" id="file3">
                    <label class="custom-file-label" for="file3">Добавить третье фото</label>
                </div>
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <input type="hidden" name="id" value="${product.id}"/>
            <input type="hidden" name="status" value="${product.status}"/>
            <button type="submit" style="width: 100%" class="btn btn-dark">Сохранить</button>
        </form>
    </div>
</@t.template>
