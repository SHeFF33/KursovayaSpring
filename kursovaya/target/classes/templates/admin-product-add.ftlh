<#import "blocks/template.ftlh" as t>

<@t.template user>
    <h1 style="text-align: center">Добавление нового товара</h1><hr>
    <form action="/admin/product/add" method="post" enctype="multipart/form-data">
        <div class="form-group">
            <label for="productBrand">Производитель</label>
            <select id="productBrand" required name="brand" class="form-control">
                <option value="">Выберите производителя</option>
                <option value="Apple">Apple</option>
                <option value="Samsung">Samsung</option>
                <option value="Baseus">Baseus</option>
                <option value="Hoco">Hoco</option>
                <option value="Honor">Honor</option>
                <option value="Xiaomi">Xiaomi</option>
                <option value="TWS">TWS</option>
                <option value="JBL">JBL</option>
                <option value="Vais">Vais</option>
                <option value="Defender">Defender</option>
                <option value="Пр. производители">Пр. производители</option>
            </select>
        </div>
        <div class="form-group">
            <label for="productName">Название</label>
            <input type="text" required class="form-control"
                   id="productName" name="title">
        </div>
        <div class="form-group">
            <label for="price">Закупочная цена (итоговая цена рассчитается автоматически)</label>
            <div class="input-group mb-3">
                <input type="number" required class="form-control" id="price" name="price" min="1">
                <div class="input-group-append">
                    <span class="input-group-text">₽</span>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label for="productCategory">Категория</label>
            <select id="productCategory" required name="category" class="form-control">
                <option value="">Выберите категорию</option>
                <option value="Зарядные устройства">Зарядные устройства</option>
                <option value="Наушники">Наушники</option>
                <option value="Чехлы">Чехлы</option>
                <option value="Защитные стекла">Защитные стекла</option>
                <option value="Пр. аксессуары">Пр. аксессуары</option>
            </select>
        </div>
        <div class="form-group">
            <label for="description">Описание</label>
            <textarea required class="form-control" id="description" name="description"></textarea>
        </div>
        <div class="form-group">
            <div class="custom-file">
                <input required type="file" name="file1" id="file1">
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
        <input type="hidden" name="status" value="В продаже" />

        <button type="submit" style="width: 100%" class="btn btn-dark">Выставить</button>

    </form>
</@t.template>
