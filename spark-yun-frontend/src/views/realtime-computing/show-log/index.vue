<template>
    <BlockModal :model-config="modelConfig" @close="closeEvent">
        <LoadingPage class="log-loading" :visible="loading">
            <div id="content" class="content-box">
                <LogContainer v-if="logMsg" :logMsg="logMsg" :status="status"></LogContainer>
            </div>
        </LoadingPage>
    </BlockModal>
</template>

<script lang="ts" setup>
import { reactive, defineExpose, ref, onUnmounted, nextTick, computed } from 'vue'
import BlockModal from '@/components/block-modal/index.vue'
import { GetRealSubLog, GetRealSubRunningLog } from '@/services/realtime-computing.service';
import LoadingPage from '@/components/loading/index.vue'

const logMsg = ref('')
const timer = ref<any>(null)
const isRequest = ref(false)
const loading = ref<boolean>(false)

const modelConfig = reactive({
    title: '日志',
    visible: false,
    width: '820px',
    cancelConfig: {
        title: '关闭',
        cancel: closeEvent,
        disabled: false
    },
    needScale: false,
    zIndex: 1100,
    customClass: 'zqy-log-modal',
    closeOnClickModal: false
})

const status = ref(false)

function showModal(clusterNodeId: string, type?: string): void {
    logMsg.value = ''
    loading.value = true
    timer.value = null

    getLogData(clusterNodeId, type)
    if (!timer.value) {
        timer.value = setInterval(() => {
            !isRequest.value && getLogData(clusterNodeId, type)
        }, 1000)
    }
    modelConfig.visible = true
}
// 获取日志
function getLogData(id: string, type?: string) {
    if (!id) {
        return
    }
    isRequest.value = true
    if (type === 'runningLog') {
        modelConfig.title = '运行日志'
        GetRealSubRunningLog({
            id: id
        }).then((res: any) => {
            loading.value = false
            status.value = ['FAIL', 'STOP'].includes(res.data.status) ? true : false
            logMsg.value = res.data.runningLog
            isRequest.value = false
        }).catch((err: any) => {
            loading.value = false
            console.log('err', err)
            logMsg.value = ''
            isRequest.value = false
        })
    } else {
        GetRealSubLog({
            id: id
        }).then((res: any) => {
            loading.value = false
            status.value = ['FAIL', 'STOP'].includes(res.data.status) ? true : false
            logMsg.value = res.data.submitLog
            isRequest.value = false
        }).catch((err: any) => {
            loading.value = false
            console.log('err', err)
            logMsg.value = ''
            isRequest.value = false
        })
    }
}

function closeEvent() {
    if (timer.value) {
        clearInterval(timer.value)
    }
    timer.value = null
    modelConfig.visible = false
}

onUnmounted(() => {
    if (timer.value) {
        clearInterval(timer.value)
    }
    timer.value = null
})


defineExpose({
    showModal
})
</script>
