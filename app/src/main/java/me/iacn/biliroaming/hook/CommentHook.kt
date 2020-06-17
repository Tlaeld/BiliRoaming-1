package me.iacn.biliroaming.hook

import android.util.Log
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedHelpers.*
import me.iacn.biliroaming.Constant.TAG
import me.iacn.biliroaming.XposedInit

/**
 * Created by iAcn on 2020/2/27
 * Email i@iacn.me
 */
class CommentHook(classLoader: ClassLoader?) : BaseHook(classLoader) {

    override fun startHook() {
        if (!XposedInit.sPrefs.getBoolean("comment_floor", false)) return
        Log.d(TAG, "startHook: Comment")

        val floorHook: XC_MethodHook = object : XC_MethodHook() {
            override fun beforeHookedMethod(param: MethodHookParam) {
                val config = getObjectField(param.thisObject, "config")
                config?.let {
                    setIntField(it, "mShowFloor", 1)
                }
            }
        }

        findAndHookMethod("com.bilibili.app.comm.comment2.model.BiliCommentList",
                mClassLoader, "isShowFloor", floorHook)
        findAndHookMethod("com.bilibili.app.comm.comment2.model.BiliCommentCursorList",
                mClassLoader, "isShowFloor", floorHook)
        findAndHookMethod("com.bilibili.app.comm.comment2.model.BiliCommentDialogue",
                mClassLoader, "isShowFloor", floorHook)
        findAndHookMethod("com.bilibili.app.comm.comment2.model.BiliCommentDetail",
                mClassLoader, "isShowFloor", floorHook)
    }
}