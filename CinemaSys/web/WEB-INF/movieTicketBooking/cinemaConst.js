var price = 32;
$(document).ready(function() {
    var $cart = $('#selected-seats'),
        $counter = $('#counter'),
        $total = $('#total');

    var sc = $('#seat-map').seatCharts({
        map: [
            'aaaaaaaaaa',
            'aaaaaaaaaa',
            'aaaaaaaaaa',
            'aaaaaaaaaa',
            'aaaaaaaaaa',
            'aaaaaaaaaa',
            'aaaaaaaaaa',
            'aaaaaaaaaa',
            'aaaaaaaaaa',
            'aa__aa__aa'
        ],

        click: function() {
            if (this.status() == 'available') {
                $('<li>' + (this.settings.row + 1) + '排' + this.settings.label + '座</li>')
                    .attr('id', 'cart-item-' + this.settings.id)
                    .data('seatId', this.settings.id)
                    .appendTo($cart);

                $counter.text(sc.find('selected').length + 1);

                $total.text(Totalamount(sc) + price);

                return 'selected';
            } else if (this.status() == 'selected') {

                $counter.text(sc.find('selected').length - 1);

                $total.text(Totalamount(sc) - price);

                $('#cart-item-' + this.settings.id).remove();

                return 'available';
            } else if (this.status() == 'unavailable') {
                return 'unavailable';
            } else {
                return this.style();
            }
        }
    });

    sc.get(['2_3', '2_4', '4_5', '7_1', '7_2', '9_4', '9_5', '9_6', '9_7', '10_5', '10_6']).status('unavailable');

});