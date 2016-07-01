/******************************************************************
 *    Package:     net.micode.notes.adapter
 *
 *    Filename:    WxhotAdapter.java
 *
 *    @version:    1.0.0
 *
 *    Create at:   2016年6月30日 下午1:51:40
 *
 *    Revision:
 *
 *    2016年6月30日 下午1:51:40
 *        - second revision
 *
 *****************************************************************/
package net.micode.notes.adapter;

import java.util.List;

import net.micode.notes.R;
import net.micode.notes.adapter.WxhotAdapter.WxhotViewHolder;
import net.micode.notes.entities.WxhotArticle;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @ClassName WxhotAdapter
 * @Description TODO(这里用一句话描述这个类的作用)
 * @Date 2016年6月30日 下午1:51:40
 * @version 1.0.0
 */
public class WxhotAdapter extends RecyclerView.Adapter<WxhotViewHolder> {

    private List<WxhotArticle> wxhotList;
    private LayoutInflater inflater;

    public WxhotAdapter(Context context, List<WxhotArticle> wxhotList) {
        this.inflater = LayoutInflater.from(context);
        this.wxhotList = wxhotList;
    }

    /*
     * (非 Javadoc) Description:
     * @see android.support.v7.widget.RecyclerView.Adapter#getItemCount()
     */
    @Override
    public int getItemCount() {
        return wxhotList.size() == 0 ? 0 : wxhotList.size() + 1;
    }

    /*
     * (非 Javadoc) Description:
     * @see
     * android.support.v7.widget.RecyclerView.Adapter#onBindViewHolder(android.support.v7.widget.RecyclerView.ViewHolder
     * , int)
     */
    @Override
    public void onBindViewHolder(WxhotViewHolder holder, int position) {
        holder.titile.setText(wxhotList.get(position).getTitle());
    }

    /*
     * (非 Javadoc) Description:
     * @see android.support.v7.widget.RecyclerView.Adapter#onCreateViewHolder(android.view.ViewGroup, int)
     */
    @Override
    public WxhotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        WxhotViewHolder wxhotViewHolder = new WxhotViewHolder(inflater.inflate(R.layout.adapter_item_wxhot, parent,
                false));
        return wxhotViewHolder;
    }

    class WxhotViewHolder extends ViewHolder {

        TextView titile;

        public WxhotViewHolder(View itemView) {
            super(itemView);
            titile = (TextView) itemView.findViewById(R.id.tv_title);
        }

    }
}
