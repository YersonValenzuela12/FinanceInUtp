package com.example.financeutp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.financeutp.R;
import com.example.financeutp.model.Movimiento;

import java.util.List;

public class AdapterMovimiento extends RecyclerView.Adapter<AdapterMovimiento.MyViewHolder> {
    List<Movimiento> movimientos;
    Context context;

    public AdapterMovimiento(List<Movimiento> movimentacoes, Context context) {
        this.movimientos = movimentacoes;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterMovimiento.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_movimiento, parent, false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterMovimiento.MyViewHolder holder, int position) {
        Movimiento movimentacao = movimientos.get(position);

        holder.titulo.setText(movimentacao.getDescription());
        holder.valor.setText(String.valueOf(movimentacao.getValue()));
        holder.categoria.setText(movimentacao.getCategory());
        holder.valor.setTextColor(context.getResources().getColor(R.color.colorAccent));

        if (movimentacao.getTipo().equals("d")) {
            holder.valor.setTextColor(context.getResources().getColor(R.color.colorAccent));
            holder.valor.setText("-" + movimentacao.getValue());
        }
    }

    @Override
    public int getItemCount() {
        return movimientos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView titulo, valor, categoria;

        public MyViewHolder(View itemView) {
            super(itemView);

            titulo = itemView.findViewById(R.id.textAdapterTitulo);
            valor = itemView.findViewById(R.id.textAdapterValor);
            categoria = itemView.findViewById(R.id.textAdapterCategoria);
        }

    }

}
