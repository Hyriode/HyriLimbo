package fr.hyriode.api;

import fr.hyriode.api.config.IHyriAPIConfig;
import fr.hyriode.api.impl.common.CHyriAPIImpl;
import fr.hyriode.api.limbo.IHyriLimbo;
import fr.hyriode.api.server.join.IHyriJoinManager;
import fr.hyriode.hyggdrasil.api.protocol.data.HyggEnv;
import fr.hyriode.limbo.HyriLimbo;

import java.util.logging.Level;

/**
 * Created by AstFaster
 * on 05/01/2023 at 21:11
 */
public class HyriAPIImpl extends CHyriAPIImpl {

    private LimboHandler limbo;

    public HyriAPIImpl(IHyriAPIConfig config) {
        super(config);

        this.preInit();
        this.init(null);
        this.postInit();
    }

    @Override
    protected void init(HyggEnv environment) {
        super.init(environment);

        this.limbo = new LimboHandler(this.hyggdrasilManager.withHyggdrasil() ? this.hyggdrasilManager.getApplication() : null);
    }

    @Override
    public void log(Level level, String message) {
        HyriLimbo.get().getLogger().log(level, message);
    }

    @Override
    public IHyriLimbo getLimbo() {
        return this.limbo;
    }

    @Override
    public IHyriJoinManager getJoinManager() {
        return null;
    }

}
